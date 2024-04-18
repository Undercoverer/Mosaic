package gay.extremist.mosaic.Util

import gay.extremist.mosaic.CLIENT
import gay.extremist.mosaic.data_models.ErrorResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

suspend inline fun <reified ReturnType> getRequest(
    urlString: String,
    noinline setHeaders: HeadersBuilder.() -> Unit = {},
    noinline setQueryParameters: URLBuilder.() -> Unit = {},
    onSuccess: (ReturnType) -> Unit = {},
    onError: (ErrorResponse) -> Unit = {},
    onNull: () -> Unit = {}
): ReturnType? {
    val responseBody = CLIENT.get(urlString){
        headers(setHeaders)
        url(setQueryParameters)
    }.bodyAsText()

    return validateResponse<ReturnType>(
        responseBody = responseBody,
        onSuccess = onSuccess,
        onError =  onError,
        onNull = onNull
    )
}

suspend inline fun <reified BodyType, reified ReturnType> postRequest(
    urlString: String,
    noinline setHeaders: HeadersBuilder.() -> Unit = {},
    setBody: () -> BodyType,
    onSuccess: (ReturnType) -> Unit = {},
    onError: (ErrorResponse) -> Unit = {},
    onNull: () -> Unit = {}
): ReturnType?{
    val responseBody = CLIENT.post(urlString){
        contentType(ContentType.Application.Json)
        headers(setHeaders)
        setBody(
            setBody()
        )
    }.bodyAsText()

    return validateResponse<ReturnType>(
        responseBody = responseBody,
        onSuccess = onSuccess,
        onError =  onError,
        onNull = onNull
    )

}

suspend inline fun <reified ReturnType> postRequest(
    urlString: String,
    noinline setHeaders: HeadersBuilder.() -> Unit = {},
    onSuccess: (ReturnType) -> Unit = {},
    onError: (ErrorResponse) -> Unit = {},
    onNull: () -> Unit = {}
): ReturnType?{
    val responseBody = CLIENT.post(urlString){
        contentType(ContentType.Application.Json)
        headers(setHeaders)
    }.bodyAsText()

    return validateResponse<ReturnType>(
        responseBody = responseBody,
        onSuccess = onSuccess,
        onError =  onError,
        onNull = onNull
    )

}

suspend inline fun getRequestText(
    urlString: String,
    noinline setHeaders: HeadersBuilder.() -> Unit = {},
    noinline setQueryParameters: URLBuilder.() -> Unit = {},
    onSuccess: (String) -> Unit = {},
    onError: (ErrorResponse) -> Unit = {},
    onNull: () -> Unit = {}
): String? {
    val response = CLIENT.get(urlString){
        headers(setHeaders)
        url(setQueryParameters)
    }

    return validateResponseText(
        response = response,
        onSuccess = onSuccess,
        onError =  onError,
        onNull = onNull
    )
}

suspend inline fun postRequestText(
    urlString: String,
    noinline setHeaders: HeadersBuilder.() -> Unit = {},
    onSuccess: (String) -> Unit = {},
    onError: (ErrorResponse) -> Unit = {},
    onNull: () -> Unit = {}
): String?{
    val response = CLIENT.post(urlString){
        contentType(ContentType.Application.Json)
        headers(setHeaders)
    }

    return validateResponseText(
        response = response,
        onSuccess = onSuccess,
        onError =  onError,
        onNull = onNull
    )

}

suspend inline fun deleteRequest(
    urlString: String,
    noinline setHeaders: HeadersBuilder.() -> Unit = {},
    onSuccess: () -> Unit = {},
    onError: (ErrorResponse) -> Unit = {},
    onNull: () -> Unit = {}
): Boolean{
    val response = CLIENT.delete(urlString){
        contentType(ContentType.Application.Json)
        headers(setHeaders)
    }

    when(response.status){
        HttpStatusCode.OK -> {
            onSuccess()
            return true
        }
        else -> {
            val errorResponse = runCatching {
                Json.decodeFromString<ErrorResponse>(response.bodyAsText())
            }.getOrNull()

            when(errorResponse){
                is ErrorResponse -> {
                    onError(errorResponse)
                    return false
                }
                else -> {
                    onNull()
                    return false
                }
            }
        }
    }

}

suspend inline fun <reified BodyType>putRequest(
    urlString: String,
    noinline setHeaders: HeadersBuilder.() -> Unit = {},
    setBody: () -> BodyType,
    onSuccess: (String) -> Unit = {},
    onError: (ErrorResponse) -> Unit = {},
    onNull: () -> Unit = {}
): String?{
    val response = CLIENT.put(urlString){
        contentType(ContentType.Application.Json)
        headers(setHeaders)
        setBody(
            setBody()
        )
    }

    return validateResponseText(
        response = response,
        onSuccess = onSuccess,
        onError = onError,
        onNull = onNull
    )

}

suspend inline fun <reified ReturnType> validateResponse(
    responseBody: String,
    onSuccess: (ReturnType) -> Unit = {},
    onError: (ErrorResponse) -> Unit = {},
    onNull: () -> Unit = {}
): ReturnType? {
    val response = runCatching {
        Json.decodeFromString<ReturnType>(responseBody)
    }.recoverCatching {
        Json.decodeFromString<ErrorResponse>(responseBody)
    }.getOrNull()


    when(response) {
        is ReturnType -> { onSuccess(response); return response }
        is ErrorResponse -> { onError(response); return null }
        else -> { onNull(); return null }
    }
}

suspend inline fun validateResponseText(
    response: HttpResponse,
    onSuccess: (String) -> Unit = {},
    onError: (ErrorResponse) -> Unit = {},
    onNull: () -> Unit = {}
): String? {
    val parsedResponse = runCatching {
        Json.decodeFromString<ErrorResponse>(response.bodyAsText())
    }.recoverCatching {
        if(response.status == HttpStatusCode.OK){
            response.bodyAsText()
        } else {
            null
        }
    }.getOrNull()

    when(parsedResponse) {
        is String -> { onSuccess(parsedResponse); return parsedResponse }
        is ErrorResponse -> { onError(parsedResponse); return parsedResponse.message }
        else -> { onNull(); return null }
    }
}
package gay.extremist.mosaic.Util

import gay.extremist.mosaic.CLIENT
import gay.extremist.mosaic.data_models.ErrorResponse
import gay.extremist.mosaic.data_models.RegisteredAccount
import gay.extremist.mosaic.data_models.RegistrationAccount
import gay.extremist.mosaic.data_models.VideoResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import org.w3c.dom.set
import kotlin.math.sqrt

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
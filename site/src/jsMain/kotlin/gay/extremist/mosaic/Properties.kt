package gay.extremist.mosaic

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.*
import io.ktor.serialization.kotlinx.json.*

const val BASE_URL = "http://localhost:8080/"


@OptIn(ExperimentalSerializationApi::class)
val CLIENT = HttpClient(Js) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            isLenient = true
        })
    }
    defaultRequest {
        url(BASE_URL)
        header("Accept", "application/json")
        header("Content-Type", "application/json")
    }
}
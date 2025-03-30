package dev.chirchir.data.common.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class IBIHttpClientImpl: IBIHttpClient {

    companion object {
        private const val BASE_URL = "https://dummyjson.com/"
        private const val TIME_OUT = 60_000L
        private const val TAG = "NetworkClient"
    }

    override fun getClient(): HttpClient {
        return HttpClient() {
            install(HttpTimeout) {
                connectTimeoutMillis = TIME_OUT
                requestTimeoutMillis = TIME_OUT
                socketTimeoutMillis = TIME_OUT
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(DefaultRequest) {
                url(BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d(TAG, message)
                    }
                }
                level = LogLevel.HEADERS
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d(TAG, "HTTP status: ${response.status.value}")
                }
            }
        }
    }
}

interface IBIHttpClient {
    fun getClient(): HttpClient
}
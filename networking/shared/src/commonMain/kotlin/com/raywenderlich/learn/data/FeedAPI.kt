package com.raywenderlich.learn.data

import com.raywenderlich.learn.APP_NAME
import com.raywenderlich.learn.X_APP_NAME
import com.raywenderlich.learn.data.model.GravatarProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlin.native.concurrent.ThreadLocal
import kotlinx.serialization.json.Json

const val GRAVATAR_URL = "https://en.gravatar.com/"
const val GRAVATAR_RESPONSE_FORMAT = ".json"

@ThreadLocal
object FeedAPI {

    private val nonStrictJson = Json { isLenient = true; ignoreUnknownKeys = true }

    private val client: HttpClient = HttpClient {
        defaultRequest {
            header(X_APP_NAME, APP_NAME)
        }
        install(ContentNegotiation){
            json(nonStrictJson)
        }
        install(Logging){
            logger = HttpClientLogger
            level = LogLevel.ALL
        }
    }

    suspend fun fetchRWEntry(feedUrl: String): HttpResponse = client.get(feedUrl)

    suspend fun fetchMyGravatar(hash: String): GravatarProfile =
        client.get("$GRAVATAR_URL$hash$GRAVATAR_RESPONSE_FORMAT").body() //https://en.gravatar.com/6ace76755c57ac756.json
}

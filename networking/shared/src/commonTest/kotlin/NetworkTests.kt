import com.raywenderlich.learn.data.GRAVATAR_RESPONSE_FORMAT
import com.raywenderlich.learn.data.GRAVATAR_URL
import com.raywenderlich.learn.data.model.GravatarEntry
import com.raywenderlich.learn.data.model.GravatarProfile
import com.raywenderlich.learn.platform.runTest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

private val profile = GravatarProfile(
    entry = listOf(
        GravatarEntry(
            id = "1000",
            hash = "1000",
            preferredUsername = "Ray Wenderlich",
            thumbnailUrl = "https://avatars.githubusercontent.com/u/4722515?s=200&v=4"
        )
    )
)

private val fake_profile = GravatarProfile(
    entry = listOf(
        GravatarEntry(
            id = "1000",
            hash = "100000",
            preferredUsername = "Ray Wenderlich",
            thumbnailUrl = "https://avatars.githubusercontent.com/u/4722515?s=200&v=4"
        )
    )
)

private val nonStrictJson = Json { isLenient = true; ignoreUnknownKeys = true }

private fun getHttpClient(): HttpClient {
    return HttpClient(MockEngine) {

        install(ContentNegotiation) {
            json(nonStrictJson)
        }

        engine {
            addHandler { request ->
                if (request.url.toString().contains(GRAVATAR_URL)) {
                    respond(
                        content = Json.encodeToString(profile),
                        headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()))
                }
                else {
                    error("Unhandled ${request.url}")
                }
            }
        }
    }
}



class NetworkTests {

    @Test
    fun testFetchMyGravatar() = runTest {
        val client = getHttpClient()
        assertEquals(profile, client.request("$GRAVATAR_URL${profile.entry[0].hash}$GRAVATAR_RESPONSE_FORMAT").body())
        assertNotEquals(fake_profile, client.request("$GRAVATAR_URL${profile.entry[0].hash}$GRAVATAR_RESPONSE_FORMAT").body())
    }

}
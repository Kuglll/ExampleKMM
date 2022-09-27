import com.raywenderlich.learn.data.RWSerializer
import com.raywenderlich.learn.data.model.PLATFORM
import com.raywenderlich.learn.data.model.RWContent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.serializersModuleOf
import kotlinx.serialization.serializer

class SerializationTests {

    @Test
    fun testEncodePlatformAll() {
        val data = RWContent(
            platform = PLATFORM.ALL,
            url = "https://www.raywenderlich.com/feed.xml",
            image = "https://assets.carolus.raywenderlich.com/assets/razeware_460-308933a0bda63e3e327123cab8002c0383a714cd35a10ade9bae9ca20b1f438b.png"
        )

        val decoded = Json.encodeToString(RWContent.serializer(), data)

        val content = "{\"platform\":\"all\",\"url\":\"https://www.raywenderlich.com/feed.xml\",\"image\":\"https://assets.carolus.raywenderlich.com/assets/razeware_460-308933a0bda63e3e327123cab8002c0383a714cd35a10ade9bae9ca20b1f438b.png\"}"
        assertEquals(content, decoded)
    }

    @Test
    fun testDecodePlatformAll() {
        val data = "{\"platform\":\"all\",\"url\":\"https://www.raywenderlich.com/feed.xml\",\"image\":\"https://assets.carolus.raywenderlich.com/assets/razeware_460-308933a0bda63e3e327123cab8002c0383a714cd35a10ade9bae9ca20b1f438b.png\"}"

        val decoded = Json.decodeFromString(RWContent.serializer(), data)
        val content = RWContent(
            platform = PLATFORM.ALL,
            url = "https://www.raywenderlich.com/feed.xml",
            image = "https://assets.carolus.raywenderlich.com/assets/razeware_460-308933a0bda63e3e327123cab8002c0383a714cd35a10ade9bae9ca20b1f438b.png"
        )

        assertEquals(content, decoded)
    }

    private val serializers = serializersModuleOf(PLATFORM::class, RWSerializer)

    @Test
    fun testEncodeCustomPlatformAll() {
        val data = PLATFORM.ALL

        val encoded = Json.encodeToString(serializers.serializer(), data)
        val expectedString = "\"all\""
        assertEquals(expectedString, encoded)
    }

    @Test
    fun testDecodeCustomPlatformAll() {
        val data = PLATFORM.ALL

        val decoded = Json.decodeFromString<PLATFORM>(data.value)
        assertEquals(decoded, data)
    }


}
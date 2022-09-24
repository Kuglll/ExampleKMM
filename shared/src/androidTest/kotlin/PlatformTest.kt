import com.example.gettingstartedwithkmm.Platform
import kotlin.test.assertEquals
import org.junit.Test

actual class PlatformTest {
    private val platform = Platform()

    @Test
    actual fun testOperatingSystemName() {
        assertEquals("Android", platform.osName)
    }
}
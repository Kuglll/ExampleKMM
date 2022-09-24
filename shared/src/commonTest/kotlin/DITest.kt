import com.example.gettingstartedwithkmm.Modules
import kotlin.test.AfterTest
import kotlin.test.Test
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules

class DITest {

    @Test
    fun testAllModules(){
        koinApplication{
            modules(
                Modules.platforms,
                Modules.viewModels,
                Modules.repositories
            )
        }.checkModules()
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

}
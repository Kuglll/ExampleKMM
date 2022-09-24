import com.example.gettingstartedwithkmm.domain.reminders.RemindersViewModel
import com.example.gettingstartedwithkmm.initKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class RemindersViewModelTest : KoinTest {

    private val viewModel: RemindersViewModel by inject()

    @BeforeTest
    fun setup() {
        initKoin()
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testCreatingReminder(){
        val title = "New Title"

        viewModel.createReminder(title)

        val count = viewModel.reminders.count {
            it.title == title
        }

        assertTrue(
            actual = count == 1,
            message = "Reminder with title: $title wasn't created.",
        )

    }

}
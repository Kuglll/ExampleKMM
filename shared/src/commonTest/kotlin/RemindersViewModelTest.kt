import com.example.gettingstartedwithkmm.domain.reminders.RemindersViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class RemindersViewModelTest {

    private lateinit var viewModel: RemindersViewModel

    @BeforeTest
    fun setup(){
        viewModel = RemindersViewModel()
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
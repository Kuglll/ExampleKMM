package com.example.gettingstartedwithkmm.android

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.gettingstartedwithkmm.Greeting
import com.example.gettingstartedwithkmm.domain.models.Reminder
import com.example.gettingstartedwithkmm.domain.reminders.RemindersViewModel
import com.example.gettingstartedwithkmm.initKoin
import com.example.gettingstartedwithkmm.ui.shared.base.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFFBB86FC),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    } else {
        lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKoin(
            viewModelsModule = module {
                viewModel {
                    RemindersViewModel(get())
                }
                viewModel {
                    MainViewModel(get(), get())
                }
            },
            appModule = module {
                single<Context> { this@MainActivity }

                single<SharedPreferences> {
                    get<Context>().getSharedPreferences(
                        "MyApp",
                        Context.MODE_PRIVATE
                    )
                }
            }
        )

        setContent {
            MyApplicationTheme {
                //RemindersView()
                MainActivityContent(lifecycleScope)
            }
        }
    }
}

@Composable
fun MainActivityContent(
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: MainViewModel = getViewModel()
) {

    var text by remember { mutableStateOf(Greeting().startMessage()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = text)
            Button(onClick = {
                lifecycleScope.launch {
                    kotlin.runCatching {
                        Greeting().greeting()
                    }.onSuccess {
                        text = it
                    }
                }
            }) {
                Text(text = "Trigger API call")
            }
            LazyColumn {
                items(viewModel.items) {
                    RowView(title = it.title, subtitle = it.subtitle)
                }
            }
            Text(text = "This app was first opened: ${viewModel.firstOpening}")
        }
    }

}

@Composable
private fun RowView(
    title: String,
    subtitle: String,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.caption,
                color = Color.Gray,
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.body1,
            )
        }
        Divider()
    }
}

@Composable
fun RemindersView(
    viewModel: RemindersViewModel = getViewModel()
) {
    Column {
        ContentView(viewModel = viewModel)
    }
}

@Composable
private fun ContentView(viewModel: RemindersViewModel) {
    var reminders by remember {
        mutableStateOf(listOf<Reminder>())
    }

    var textFieldValue by remember { mutableStateOf("") }

    viewModel.onRemindersUpdated = {
        reminders = it
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp)
    ) {
        items(items = reminders) { item ->

            val onItemClick = {
                viewModel.markReminder(id = item.id, isCompleted = !item.isCompleted)
            }

            ReminderItem(
                title = item.title,
                isCompleted = item.isCompleted,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = true, onClick = onItemClick)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        item {
            val onSubmit = {
                viewModel.createReminder(title = textFieldValue)
                textFieldValue = ""
            }
            TextField(value = textFieldValue, onValueChange = {
                textFieldValue = it
            })
            Button(onClick = onSubmit) {
                Text(text = "Add reminder")
            }
        }
    }
}

@Composable
private fun ReminderItem(title: String, isCompleted: Boolean, modifier: Modifier) {
    var _isCompleted by remember { mutableStateOf(isCompleted) }

    Row(modifier = modifier) {
        Text(text = title)
        Checkbox(checked = _isCompleted, onCheckedChange = {
            _isCompleted = it
        })
    }
}
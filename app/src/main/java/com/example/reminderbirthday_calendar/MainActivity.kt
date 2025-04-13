package com.example.reminderbirthday_calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.data.local.util.zodiac.ZodiacCalculatorImpl
import com.example.domain.util.zodiac.ZodiacCalculator
import com.example.reminderbirthday_calendar.ui.theme.ReminderBirthday_CalendarTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val zodiacCalculator: ZodiacCalculator = ZodiacCalculatorImpl(context = this)

        // Пример получения знака зодиака по дате
        val date = LocalDate.of(2006, 1, 3)  // Пример даты
        val westernZodiac = zodiacCalculator.getWesternZodiac(date)
        val chineseZodiac = zodiacCalculator.getChineseZodiac(date)

        setContent {
            ReminderBirthday_CalendarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        info = "$westernZodiac, $chineseZodiac",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(info: String, modifier: Modifier = Modifier) {
    Text(
        text = info,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReminderBirthday_CalendarTheme {
        Greeting("Android")
    }
}
package com.artkotlindev.reminderbirthday_calendar.presentation.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artkotlindev.domain.models.settings.ThemeType
import com.artkotlindev.reminderbirthday_calendar.LocalTheme
import com.artkotlindev.reminderbirthday_calendar.LocalizedContext
import com.artkotlindev.reminderbirthday_calendar.R
import com.artkotlindev.reminderbirthday_calendar.ui.theme.darkRed
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun RowDaysOfWeek(
    modifier: Modifier = Modifier
) {
    val daysOfWeek = DayOfWeek.entries

    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        daysOfWeek.forEach { day ->
            Text(
                text = LocalizedContext.current.resources
                    .getStringArray(R.array.days_of_week_short)[day.value - 1],
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                color = if (day == LocalDate.now().dayOfWeek) {
                    MaterialTheme.colorScheme.primary
                } else if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                    darkRed
                } else {
                    if (LocalTheme.current == ThemeType.DARK) Color.White else Color.Black
                },
                fontFamily = FontFamily.Serif,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun RowDaysOfWeekPreview() {
    RowDaysOfWeek(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    )
}

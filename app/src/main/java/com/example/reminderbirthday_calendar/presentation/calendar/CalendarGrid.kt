package com.example.reminderbirthday_calendar.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.domain.models.settings.ThemeType
import com.example.reminderbirthday_calendar.LocalTheme
import com.example.reminderbirthday_calendar.ui.theme.darkRed
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarGrid(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
){
    val daysOfMonth = currentMonth.lengthOfMonth()
    val offsetFirstDayOfWeek = currentMonth.atDay(1).dayOfWeek.value - 1

    LazyVerticalGrid(
        modifier = Modifier.then(modifier),
        columns = GridCells.Fixed(7)
    ) {
        items(
            count = offsetFirstDayOfWeek,
            key = { num -> "DayInLastMonth_$num" }
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) { }
        }

        items(
            count = daysOfMonth,
            key = { num -> "DayInThisMonth_$num" }
        ) { dayIndex ->
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .padding(6.dp)
                    .clip(shape = CircleShape)
                    .background(
                        color = if (dayIndex + 1 == selectedDate?.dayOfMonth)
                            MaterialTheme.colorScheme.primary
                        else Color.Transparent,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = if (currentMonth.atDay(dayIndex + 1) == LocalDate.now()) MaterialTheme.colorScheme.primary
                            else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable(onClick = { onDateSelected(currentMonth.atDay(dayIndex + 1)) })
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (dayIndex + 1).toString(),
                    color = if (currentMonth.atDay(dayIndex + 1).dayOfWeek == DayOfWeek.SATURDAY ||
                        currentMonth.atDay(dayIndex + 1).dayOfWeek == DayOfWeek.SUNDAY) { darkRed }
                        else if (dayIndex + 1 == selectedDate?.dayOfMonth) {
                            if (LocalTheme.current == ThemeType.DARK) Color.Black else Color.White
                        }
                    else { if (LocalTheme.current == ThemeType.DARK) Color.White else Color.Black },
                    fontFamily = FontFamily.Serif,
                )
            }
        }
    }
}
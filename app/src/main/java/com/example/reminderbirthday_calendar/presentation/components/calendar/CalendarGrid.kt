package com.example.reminderbirthday_calendar.presentation.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.domain.models.event.Event
import com.example.domain.models.event.SortTypeEvent
import com.example.domain.models.settings.ThemeType
import com.example.reminderbirthday_calendar.LocalTheme
import com.example.reminderbirthday_calendar.ui.theme.blueAzure
import com.example.reminderbirthday_calendar.ui.theme.darkGreen
import com.example.reminderbirthday_calendar.ui.theme.darkPurple
import com.example.reminderbirthday_calendar.ui.theme.darkRed
import com.example.reminderbirthday_calendar.ui.theme.yellow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarGrid(
    modifier: Modifier = Modifier,
    events: List<Event>,
    currentMonth: YearMonth,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val anyYear = 2000
    val daysOfMonth = currentMonth.lengthOfMonth()
    val offsetFirstDayOfWeek = currentMonth.atDay(1).dayOfWeek.value - 1

    val dates = events.map { it.originalDate.withYear(anyYear) }.groupingBy { it }.eachCount()

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
                    .size(45.dp)
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
                    .size(45.dp)
                    .padding(6.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (dayIndex + 1 == selectedDate?.dayOfMonth)
                            MaterialTheme.colorScheme.primary
                        else Color.Transparent,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = if (currentMonth.atDay(dayIndex + 1) == LocalDate.now())
                            MaterialTheme.colorScheme.primary
                        else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onDateSelected(currentMonth.atDay(dayIndex + 1)) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (dayIndex + 1).toString(),
                    color = when {
                        currentMonth.atDay(dayIndex + 1).dayOfWeek == DayOfWeek.SATURDAY ||
                                currentMonth.atDay(dayIndex + 1).dayOfWeek == DayOfWeek.SUNDAY -> darkRed

                        dayIndex + 1 == selectedDate?.dayOfMonth -> {
                            if (LocalTheme.current == ThemeType.DARK) Color.Black else Color.White
                        }

                        else -> {
                            if (LocalTheme.current == ThemeType.DARK) Color.White else Color.Black
                        }
                    },
                    fontFamily = FontFamily.Serif
                )

                if (currentMonth.atDay(dayIndex + 1).withYear(anyYear) in dates.keys) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .size(6.dp)
                            .offset(y=1.5.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = getSafeGradientColorsForDay(
                                        events = events,
                                        currentMonth = currentMonth,
                                        dayIndex = dayIndex,
                                        anyYear = anyYear
                                    ),
                                    center = Offset(25f, 25f),
                                    radius = 50f
                                ),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

fun getSafeGradientColorsForDay(
    events: List<Event>,
    currentMonth: YearMonth,
    dayIndex: Int,
    anyYear: Int
): List<Color> {
    val matchedColors = events
        .filter {
            it.originalDate.withYear(anyYear) == currentMonth.atDay(dayIndex + 1).withYear(anyYear)
        }
        .map { it.toColor() }

    return when {
        matchedColors.size >= 2 -> matchedColors
        matchedColors.size == 1 -> listOf(matchedColors[0], matchedColors[0])
        else -> listOf(Color.Transparent, Color.Transparent)
    }
}

fun Event.toColor(): Color = when (this.sortTypeEvent) {
    SortTypeEvent.FAMILY -> darkRed
    SortTypeEvent.RELATIVE -> yellow
    SortTypeEvent.FRIEND -> blueAzure
    SortTypeEvent.COLLEAGUE -> darkGreen
    SortTypeEvent.OTHER -> darkPurple
}


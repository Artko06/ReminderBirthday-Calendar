package com.example.reminderbirthday_calendar.presentation.components.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R
import java.time.YearMonth

@Composable
fun MonthYearText(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment,
    monthYearMonth: YearMonth
){
    val month = monthYearMonth.month
    val year = monthYearMonth.year

    Box(
        modifier = Modifier.then(modifier),
        contentAlignment = contentAlignment
    ){
        Text(
            text = LocalizedContext.current.resources
                .getStringArray(R.array.months)[month.value - 1] + " " + year,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold
        )
    }
}
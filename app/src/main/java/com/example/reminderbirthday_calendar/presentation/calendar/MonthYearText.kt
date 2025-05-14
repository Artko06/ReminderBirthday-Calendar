package com.example.reminderbirthday_calendar.presentation.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import java.time.Month
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
            text = when(month) {
                Month.JANUARY -> "JANUARY "
                Month.FEBRUARY -> "FEBRUARY "
                Month.MARCH -> "MARCH "
                Month.APRIL -> "APRIL "
                Month.MAY -> "MAY "
                Month.JUNE -> "JUNE "
                Month.JULY -> "JULY "
                Month.AUGUST -> "AUGUST "
                Month.SEPTEMBER -> "SEPTEMBER "
                Month.OCTOBER -> "OCTOBER "
                Month.NOVEMBER -> "NOVEMBER "
                Month.DECEMBER -> "DECEMBER "
            } + year,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold
        )
    }
}
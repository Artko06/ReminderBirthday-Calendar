package com.example.reminderbirthday_calendar.presentation.state

import java.time.LocalDate
import java.time.YearMonth

data class CalendarState(
    val selectDate: LocalDate? = LocalDate.now(),
    val currentMonth: YearMonth = YearMonth.now()
)
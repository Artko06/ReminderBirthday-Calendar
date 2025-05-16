package com.example.reminderbirthday_calendar.presentation.state

import com.example.domain.models.event.Event
import java.time.LocalDate
import java.time.YearMonth

data class CalendarState(
    val events: List<Event> = emptyList(),
    val eventsInDate: List<Event> = emptyList(),

    val calendarPage: Int = 0,

    val selectDate: LocalDate? = null,
    val currentMonth: YearMonth = YearMonth.now()
)
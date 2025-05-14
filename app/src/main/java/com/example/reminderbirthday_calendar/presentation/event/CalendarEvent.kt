package com.example.reminderbirthday_calendar.presentation.event

import java.time.LocalDate

sealed class CalendarEvent {
    data class SelectDate(val date: LocalDate?): CalendarEvent()
}
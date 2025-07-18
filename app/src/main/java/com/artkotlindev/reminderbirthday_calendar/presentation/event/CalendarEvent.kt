package com.artkotlindev.reminderbirthday_calendar.presentation.event

import java.time.LocalDate

sealed class CalendarEvent {
    data class SelectDate(val date: LocalDate?): CalendarEvent()
    data class ChangeCalendarPage(val newPage: Int): CalendarEvent()
}
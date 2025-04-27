package com.example.reminderbirthday_calendar.presentation.event

sealed class EventsEvent {
    object ImportEventsFromContacts: EventsEvent()
    data class UpdateSearchLine(val newValue: String): EventsEvent()
}
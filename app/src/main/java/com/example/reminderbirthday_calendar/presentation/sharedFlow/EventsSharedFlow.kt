package com.example.reminderbirthday_calendar.presentation.sharedFlow

sealed class EventsSharedFlow {
    data class ShowToast(val message: String) : EventsSharedFlow()
}
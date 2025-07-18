package com.artkotlindev.reminderbirthday_calendar.presentation.sharedFlow

sealed class EventsSharedFlow {
    data class ShowToast(
        val messageResId: Int,
        val formatArgs: List<String> = emptyList()
    ) : EventsSharedFlow()
}
package com.example.reminderbirthday_calendar.presentation.sharedFlow

sealed class AddEventSharedFlow {
    data class ShowToast(val message: String): AddEventSharedFlow()
}
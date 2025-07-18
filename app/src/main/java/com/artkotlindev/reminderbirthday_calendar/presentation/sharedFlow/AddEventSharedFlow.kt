package com.artkotlindev.reminderbirthday_calendar.presentation.sharedFlow

sealed class AddEventSharedFlow {
    data class ShowToast(
        val messageResId: Int,
        val formatArgs: List<String> = emptyList()
    ): AddEventSharedFlow()
}
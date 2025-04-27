package com.example.reminderbirthday_calendar.presentation.sharedFlow

sealed class ImportExportSharedFlow {
    data class ShowToast(val message: String) : ImportExportSharedFlow()
}
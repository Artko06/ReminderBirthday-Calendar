package com.example.reminderbirthday_calendar.presentation.event

sealed class ImportExportEvent {
    object EventsToJsonToExternalDirExport: ImportExportEvent()
    object EventsToCsvToExternalDirExport: ImportExportEvent()
}
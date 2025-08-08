package com.artkotlindev.reminderbirthday_calendar.presentation.event

import android.net.Uri

sealed class ImportExportEvent {
    object ExportEventsToJson: ImportExportEvent()
    object ExportEventsToCsv: ImportExportEvent()
    data class ImportEventsFromJson(val uri: Uri): ImportExportEvent()
    data class ImportEventsFromCsv(val uri: Uri): ImportExportEvent()
    object ImportEventsFromContacts: ImportExportEvent()

    object ShowReadContactPermDialog: ImportExportEvent()
    object CloseReadContactPermDialog: ImportExportEvent()
}
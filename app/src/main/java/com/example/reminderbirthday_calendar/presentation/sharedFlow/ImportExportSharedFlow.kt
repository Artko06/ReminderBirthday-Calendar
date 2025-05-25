package com.example.reminderbirthday_calendar.presentation.sharedFlow

import com.example.domain.models.event.Event
import com.example.reminderbirthday_calendar.intents.shareIntent.TypeShareFile

sealed class ImportExportSharedFlow {
    data class ShowToast(
        val messageResId: Int,
        val formatArgs: List<String> = emptyList()
    ) : ImportExportSharedFlow()
    data class ShowShareView(val typeShareFile: TypeShareFile): ImportExportSharedFlow()
    data class UpdateEventsAfterImport(val events: List<Event>): ImportExportSharedFlow()
}
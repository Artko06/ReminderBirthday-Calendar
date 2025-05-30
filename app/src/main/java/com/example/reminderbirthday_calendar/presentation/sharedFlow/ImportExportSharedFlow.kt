package com.example.reminderbirthday_calendar.presentation.sharedFlow

import com.example.reminderbirthday_calendar.intents.shareIntent.TypeShareFile

sealed class ImportExportSharedFlow {
    data class ShowToast(
        val messageResId: Int,
        val formatArgs: List<String> = emptyList()
    ) : ImportExportSharedFlow()
    data class ShowShareView(val typeShareFile: TypeShareFile): ImportExportSharedFlow()
}
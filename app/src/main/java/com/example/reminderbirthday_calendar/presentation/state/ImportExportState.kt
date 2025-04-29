package com.example.reminderbirthday_calendar.presentation.state

data class ImportExportState(
    val statusExportJson: Boolean? = null,
    val statusExportCsv: Boolean? = null,

    val googleAuthEmail: String? = null,
)
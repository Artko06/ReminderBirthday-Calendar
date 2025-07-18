package com.artkotlindev.reminderbirthday_calendar.presentation.state

data class ImportExportState(
    val statusExportJson: Boolean? = null,
    val statusExportCsv: Boolean? = null,

    val googleAuthEmail: String? = null,
    val isShowReadContactPermDialog: Boolean = false,

    val isLoadingReimportEvent: Boolean = false,
    val isLoadingExportJson: Boolean = false,
    val isLoadingExportCsv: Boolean = false,
    val isLoadingImportJson: Boolean = false,
    val isLoadingImportCsv: Boolean = false,
    val isLoadingRemoteExport: Boolean = false,
    val isLoadingRemoteImport: Boolean = false,
    val isLoadingSignInWithGoogle: Boolean = false
)
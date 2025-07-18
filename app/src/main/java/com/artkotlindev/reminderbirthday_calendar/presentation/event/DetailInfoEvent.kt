package com.artkotlindev.reminderbirthday_calendar.presentation.event

sealed class DetailInfoEvent {
    object OnShowDeleteDialog: DetailInfoEvent()
    object OnCloseDeleteDialog: DetailInfoEvent()
    object DeleteEvent: DetailInfoEvent()

    object OnShowNotesDialog: DetailInfoEvent()
    object OnCloseNotesDialog: DetailInfoEvent()
    data class OnChangeNotes(val newNotes: String): DetailInfoEvent()
    object SaveNewNotes: DetailInfoEvent()
}
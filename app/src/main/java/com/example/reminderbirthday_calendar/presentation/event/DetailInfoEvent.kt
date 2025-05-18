package com.example.reminderbirthday_calendar.presentation.event

sealed class DetailInfoEvent {
    object OnShowDeleteDialog: DetailInfoEvent()
    object OnCloseDeleteDialog: DetailInfoEvent()
    object DeleteEvent: DetailInfoEvent()

    object OnShowNotesDialog: DetailInfoEvent()
    object OnCloseNotesDialog: DetailInfoEvent()
    object SaveNewNotes: DetailInfoEvent()
}
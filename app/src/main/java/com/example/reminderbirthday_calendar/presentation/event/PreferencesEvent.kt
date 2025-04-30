package com.example.reminderbirthday_calendar.presentation.event

sealed class PreferencesEvent {
    object ChangeChineseZodiacStatus: PreferencesEvent()
    object ChangeWesternZodiacStatus: PreferencesEvent()
    object ShowSettingsNotificationDialog: PreferencesEvent()
    object CloseSettingsNotificationDialog: PreferencesEvent()
}
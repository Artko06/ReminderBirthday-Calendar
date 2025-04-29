package com.example.reminderbirthday_calendar.presentation.event

sealed class PreferencesEvent {
    object ChangeChineseZodiacStatus: PreferencesEvent()
    object ChangeWesternZodiacStatus: PreferencesEvent()
}
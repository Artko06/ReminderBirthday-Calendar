package com.example.reminderbirthday_calendar.presentation.event

sealed class PreferencesEvent {
    object ChangeChineseZodiacStatus: PreferencesEvent()
    object ChangeWesternZodiacStatus: PreferencesEvent()

    object ChangeStatusShowBirthdayEvent: PreferencesEvent()
    object ChangeStatusShowAnniversaryEvent: PreferencesEvent()
    object ChangeStatusShowOtherEvent: PreferencesEvent()
    object ShowStatusTypeEventDialog: PreferencesEvent()
    object CloseStatusTypeEventDialog: PreferencesEvent()

    object ShowSettingsNotificationDialog: PreferencesEvent()
    object CloseSettingsNotificationDialog: PreferencesEvent()
}
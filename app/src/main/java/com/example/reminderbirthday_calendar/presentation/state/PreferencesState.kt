package com.example.reminderbirthday_calendar.presentation.state

data class PreferencesState(
    val isEnableWesternZodiac: Boolean = false,
    val isEnableChineseZodiac: Boolean = false,
    val isEnableStatusNotification: Boolean = false,

    val isShowSettingsNotificationDialog: Boolean = false
)
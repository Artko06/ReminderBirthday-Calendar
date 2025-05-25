package com.example.reminderbirthday_calendar.presentation.event

import com.example.domain.models.settings.LanguageType
import com.example.domain.models.settings.ThemeType

sealed class PreferencesEvent {
    object ChangeChineseZodiacStatus: PreferencesEvent()
    object ChangeZodiacSignStatus: PreferencesEvent()

    object ChangeStatusShowBirthdayEvent: PreferencesEvent()
    object ChangeStatusShowAnniversaryEvent: PreferencesEvent()
    object ChangeStatusShowOtherEvent: PreferencesEvent()
    object ShowStatusTypeEventDialog: PreferencesEvent()
    object CloseStatusTypeEventDialog: PreferencesEvent()

    object ShowSettingsNotificationDialog: PreferencesEvent()
    object CloseSettingsNotificationDialog: PreferencesEvent()

    data class ChangeAppTheme(val theme: ThemeType): PreferencesEvent()
    object ShowAppThemeDialog: PreferencesEvent()
    object CloseAppThemeDialog: PreferencesEvent()

    data class ChangeAppLanguage(val language: LanguageType): PreferencesEvent()
    object ShowAppLanguageDialog: PreferencesEvent()
    object CloseAppLanguageDialog: PreferencesEvent()
}
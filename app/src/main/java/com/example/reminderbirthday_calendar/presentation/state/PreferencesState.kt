package com.example.reminderbirthday_calendar.presentation.state

import com.example.domain.models.settings.LanguageType
import com.example.domain.models.settings.ThemeType

data class PreferencesState(
    val isEnableZodiacSign: Boolean = false,
    val isEnableChineseZodiac: Boolean = false,

    val isEnableStatusNotification: Boolean = false,

    val isEnableShowBirthdayEvent: Boolean = false,
    val isEnableShowAnniversaryEvent: Boolean = false,
    val isEnableShowOtherEvent: Boolean = false,

    val isShowStatusTypeEventsDialog: Boolean = false,
    val isShowSettingsNotificationDialog: Boolean = false,
    val isShowAppThemeDialog: Boolean = false,
    val isShowAppLanguageDialog: Boolean = false,

    val selectedTheme: ThemeType = ThemeType.SYSTEM,
    val selectedLanguage: LanguageType = LanguageType.SYSTEM
)
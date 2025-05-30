package com.example.data.local.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

val IS_INITIALIZED_KEY = booleanPreferencesKey("app_initialized")
val IS_FIRST_LAUNCH_KEY = booleanPreferencesKey("app_first_launch")
val LANGUAGE_KEY = stringPreferencesKey("app_language")
val THEME_KEY = stringPreferencesKey("app_theme")
val ZODIAC_SIGN_ENABLE_KEY = booleanPreferencesKey("app_enable_zodiac_sign")
val ZODIAC_CHINESE_ENABLE_KEY = booleanPreferencesKey("app_enable_zodiac_chinese")
val VIEW_DAYS_LEFT_ENABLE_KEY = booleanPreferencesKey("app_enable_view_days_left")

val IS_SHOW_BIRTHDAY_EVENT_KEY = booleanPreferencesKey("app_show_birthday_event")
val IS_SHOW_ANNIVERSARY_EVENT_KEY = booleanPreferencesKey("app_show_anniversary_event")
val IS_SHOW_OTHER_EVENT_KEY = booleanPreferencesKey("app_show_other_event")
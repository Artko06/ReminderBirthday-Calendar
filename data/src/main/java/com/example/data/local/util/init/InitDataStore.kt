package com.example.data.local.util.init

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.data.local.preferences.IS_FIRST_LAUNCH_KEY
import com.example.data.local.preferences.IS_INITIALIZED_KEY
import com.example.data.local.preferences.IS_SHOW_ANNIVERSARY_EVENT_KEY
import com.example.data.local.preferences.IS_SHOW_BIRTHDAY_EVENT_KEY
import com.example.data.local.preferences.IS_SHOW_OTHER_EVENT_KEY
import com.example.data.local.preferences.LANGUAGE_KEY
import com.example.data.local.preferences.THEME_KEY
import com.example.data.local.preferences.VIEW_DAYS_LEFT_ENABLE_KEY
import com.example.data.local.preferences.ZODIAC_CHINESE_ENABLE_KEY
import com.example.data.local.preferences.ZODIAC_SIGN_ENABLE_KEY
import com.example.data.local.preferences.dataStore
import com.example.domain.models.settings.LanguageType
import com.example.domain.models.settings.ThemeType

object InitDataStore {
    suspend fun initDefaults(context: Context) {
        context.dataStore.edit { prefs ->
            if (prefs[IS_INITIALIZED_KEY] != true) {

                prefs[LANGUAGE_KEY] = LanguageType.SYSTEM.name
                prefs[THEME_KEY] = ThemeType.SYSTEM.name
                prefs[VIEW_DAYS_LEFT_ENABLE_KEY] = false
                prefs[ZODIAC_SIGN_ENABLE_KEY] = true
                prefs[ZODIAC_CHINESE_ENABLE_KEY] = true
                prefs[IS_FIRST_LAUNCH_KEY] = true
                prefs[IS_SHOW_BIRTHDAY_EVENT_KEY] = true
                prefs[IS_SHOW_ANNIVERSARY_EVENT_KEY] = true
                prefs[IS_SHOW_OTHER_EVENT_KEY] = true

                prefs[IS_INITIALIZED_KEY] = true
            }
        }
    }
}
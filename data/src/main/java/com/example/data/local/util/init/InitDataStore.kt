package com.example.data.local.util.init

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.local.preferences.IS_INITIALIZED_KEY
import com.example.data.local.preferences.LANGUAGE_KEY
import com.example.data.local.preferences.NOTIFICATION_ENABLE_KEY
import com.example.data.local.preferences.THEME_KEY
import com.example.data.local.preferences.ZODIAC_CHINESE_ENABLE_KEY
import com.example.data.local.preferences.ZODIAC_WESTERN_ENABLE_KEY
import com.example.domain.models.settings.LanguageType
import com.example.domain.models.settings.ThemeType

object InitDataStore {
    private val Context.dataStore by preferencesDataStore(name = "settings")

    // Init
    suspend fun initDefaults(context: Context) {
        context.dataStore.edit { prefs ->
            if (prefs[IS_INITIALIZED_KEY] != true) {

                prefs[LANGUAGE_KEY] = LanguageType.SYSTEM.name
                prefs[THEME_KEY] = ThemeType.SYSTEM.name
                prefs[NOTIFICATION_ENABLE_KEY] = true
                prefs[ZODIAC_WESTERN_ENABLE_KEY] = true
                prefs[ZODIAC_CHINESE_ENABLE_KEY] = true

                prefs[IS_INITIALIZED_KEY] = true
            }
        }
    }
}
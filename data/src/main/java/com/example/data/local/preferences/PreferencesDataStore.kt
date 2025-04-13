package com.example.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.models.settings.LanguageType
import com.example.domain.models.settings.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object PreferencesDataStore {
    private val Context.dataStore by preferencesDataStore(name = "settings")

    private val LANGUAGE_KEY = stringPreferencesKey("app_language")
    private val THEME_KEY = stringPreferencesKey("app_theme")
    private val ZODIAC_WESTERN_ENABLE_KEY = booleanPreferencesKey("app_enable_zodiac_western")
    private val ZODIAC_CHINESE_ENABLE_KEY = booleanPreferencesKey("app_enable_zodiac_chinese")
    private val NOTIFICATION_ENABLE_KEY = booleanPreferencesKey("app_enable_notification")

    // Languages
    fun getLanguage(context: Context): Flow<LanguageType> {
        return context.dataStore.data.map { prefs ->
            prefs[LANGUAGE_KEY]?.let { LanguageType.valueOf(it) } ?: LanguageType.SYSTEM
        }
    }

    suspend fun setLanguage(context: Context, language: LanguageType) {
        context.dataStore.edit { it[LANGUAGE_KEY] = language.name }
    }

    // Theme
    fun getTheme(context: Context): Flow<ThemeType> {
        return context.dataStore.data.map { prefs ->
            prefs[THEME_KEY]?.let { ThemeType.valueOf(it) } ?: ThemeType.SYSTEM
        }
    }

    suspend fun setTheme(context: Context, theme: ThemeType) {
        context.dataStore.edit { it[THEME_KEY] = theme.name }
    }

    // Notification
    fun getStatusNotification(context: Context): Flow<Boolean>{
        return context.dataStore.data.map { prefs ->
            prefs[NOTIFICATION_ENABLE_KEY] ?: false
        }
    }

    suspend fun setStatusNotification(context: Context, activeStatus: Boolean){
        context.dataStore.edit { it[NOTIFICATION_ENABLE_KEY] = activeStatus }
    }

    // Zodiac
    fun getStatusZodiacWestern(context: Context): Flow<Boolean>{
        return context.dataStore.data.map { prefs ->
            prefs[ZODIAC_WESTERN_ENABLE_KEY] ?: false
        }
    }

    suspend fun setStatusZodiacWestern(context: Context, activeStatus: Boolean){
        context.dataStore.edit { it[ZODIAC_WESTERN_ENABLE_KEY] = activeStatus }
    }

    fun getStatusZodiacChinese(context: Context): Flow<Boolean>{
        return context.dataStore.data.map { prefs ->
            prefs[ZODIAC_CHINESE_ENABLE_KEY] ?: false
        }
    }

    suspend fun setStatusZodiacChinese(context: Context, activeStatus: Boolean){
        context.dataStore.edit { it[ZODIAC_CHINESE_ENABLE_KEY] = activeStatus }
    }
}
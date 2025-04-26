package com.example.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.domain.models.settings.LanguageType
import com.example.domain.models.settings.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object PreferencesDataStore {
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

    // First launch
    fun getIsFirstLaunch(context: Context): Flow<Boolean>{
        return context.dataStore.data.map { prefs ->
            prefs[IS_FIRST_LAUNCH_KEY] ?: false
        }
    }

    suspend fun setIsFirstLaunch(context: Context, isFirstLaunch: Boolean) {
        context.dataStore.edit { it[IS_FIRST_LAUNCH_KEY] = isFirstLaunch }
    }
}
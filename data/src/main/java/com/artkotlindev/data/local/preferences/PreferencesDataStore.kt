package com.artkotlindev.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.artkotlindev.domain.models.settings.LanguageType
import com.artkotlindev.domain.models.settings.ThemeType
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

    // View days left
    fun getStatusViewDaysLeft(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[VIEW_DAYS_LEFT_ENABLE_KEY] ?: false
        }
    }

    suspend fun setStatusViewDaysLeft(context: Context, activeStatus: Boolean) {
        context.dataStore.edit { it[VIEW_DAYS_LEFT_ENABLE_KEY] = activeStatus }
    }

    // Show type event
    fun getStatusShowBirthdayEvent(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[IS_SHOW_BIRTHDAY_EVENT_KEY] ?: false
        }
    }

    suspend fun setStatusShowBirthdayEvent(context: Context, activeStatus: Boolean) {
        context.dataStore.edit { it[IS_SHOW_BIRTHDAY_EVENT_KEY] = activeStatus }
    }

    fun getStatusShowAnniversaryEvent(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[IS_SHOW_ANNIVERSARY_EVENT_KEY] ?: false
        }
    }

    suspend fun setStatusShowAnniversaryEvent(context: Context, activeStatus: Boolean) {
        context.dataStore.edit { it[IS_SHOW_ANNIVERSARY_EVENT_KEY] = activeStatus }
    }

    fun getStatusShowOtherEvent(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[IS_SHOW_OTHER_EVENT_KEY] ?: false
        }
    }

    suspend fun setStatusShowOtherEvent(context: Context, activeStatus: Boolean) {
        context.dataStore.edit { it[IS_SHOW_OTHER_EVENT_KEY] = activeStatus }
    }

    // Zodiac
    fun getStatusZodiacSign(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[ZODIAC_SIGN_ENABLE_KEY] ?: false
        }
    }

    suspend fun setStatusZodiacWestern(context: Context, activeStatus: Boolean) {
        context.dataStore.edit { it[ZODIAC_SIGN_ENABLE_KEY] = activeStatus }
    }

    fun getStatusZodiacChinese(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[ZODIAC_CHINESE_ENABLE_KEY] ?: false
        }
    }

    suspend fun setStatusZodiacChinese(context: Context, activeStatus: Boolean) {
        context.dataStore.edit { it[ZODIAC_CHINESE_ENABLE_KEY] = activeStatus }
    }

    // First launch
    fun getIsFirstLaunch(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[IS_FIRST_LAUNCH_KEY] ?: false
        }
    }

    suspend fun setIsFirstLaunch(context: Context, isFirstLaunch: Boolean) {
        context.dataStore.edit { it[IS_FIRST_LAUNCH_KEY] = isFirstLaunch }
    }
}
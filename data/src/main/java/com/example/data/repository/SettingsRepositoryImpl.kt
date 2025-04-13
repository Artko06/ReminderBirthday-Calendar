package com.example.data.repository

import android.content.Context
import com.example.data.local.preferences.PreferencesDataStore
import com.example.domain.models.settings.LanguageType
import com.example.domain.models.settings.ThemeType
import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val context: Context
): SettingsRepository
{
    // Language
    override fun getLanguage(): Flow<LanguageType> {
        return PreferencesDataStore.getLanguage(context = context)
    }

    override suspend fun saveLanguage(language: LanguageType) {
        PreferencesDataStore.setLanguage(context = context, language = language)
    }

    // Theme
    override fun getTheme(): Flow<ThemeType> {
        return PreferencesDataStore.getTheme(context = context)
    }

    override suspend fun setTheme(theme: ThemeType) {
        PreferencesDataStore.setTheme(context = context, theme = theme)
    }

    // Notification
    override fun getStatusNotification(): Flow<Boolean> {
        return PreferencesDataStore.getStatusNotification(context = context)
    }

    override suspend fun setStatusNotification(activeStatus: Boolean) {
        PreferencesDataStore.setStatusNotification(context = context, activeStatus = activeStatus)
    }
}
package com.example.domain.repository

import com.example.domain.models.settings.LanguageType
import com.example.domain.models.settings.ThemeType
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    // Language
    fun getLanguage(): Flow<LanguageType>
    suspend fun saveLanguage(language: LanguageType)

    // Theme
    fun getTheme(): Flow<ThemeType>
    suspend fun setTheme(theme: ThemeType)

    // Notification
    fun getStatusNotification(): Flow<Boolean>
    suspend fun setStatusNotification(activeStatus: Boolean)
}
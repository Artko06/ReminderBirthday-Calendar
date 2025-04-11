package com.example.domain.repository

import com.example.domain.models.settings.LanguageType
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    // Language
    fun getLanguage(): Flow<LanguageType>
    suspend fun saveLanguage(language: LanguageType)

    // Theme
}
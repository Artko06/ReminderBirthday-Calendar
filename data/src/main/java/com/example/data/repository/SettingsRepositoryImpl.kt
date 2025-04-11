package com.example.data.repository

import com.example.data.local.dao.SettingsDao
import com.example.data.local.entity.settings.LanguageEntity
import com.example.domain.models.settings.LanguageType
import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dao: SettingsDao
): SettingsRepository
{
    override fun getLanguage(): Flow<LanguageType> {
        return dao.getLanguageType().map { stringValue ->
            enumValueOf<LanguageType>(stringValue)
        }
    }

    override suspend fun saveLanguage(language: LanguageType) {
        dao.upsertLanguage(
            language = LanguageEntity(languageType = language.name)
        )
    }
}
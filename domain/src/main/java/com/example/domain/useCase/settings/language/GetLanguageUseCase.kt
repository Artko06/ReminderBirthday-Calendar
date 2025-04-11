package com.example.domain.useCase.settings.language

import com.example.domain.models.settings.LanguageType
import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetLanguageUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<LanguageType> = repository.getLanguage()
}
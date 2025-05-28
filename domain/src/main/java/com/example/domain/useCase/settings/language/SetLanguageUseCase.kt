package com.example.domain.useCase.settings.language

import com.example.domain.models.settings.LanguageType
import com.example.domain.repository.local.SettingsRepository

class SetLanguageUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(language: LanguageType) = repository.saveLanguage(language = language)
}
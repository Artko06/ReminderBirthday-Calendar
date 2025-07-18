package com.artkotlindev.domain.useCase.settings.language

import com.artkotlindev.domain.models.settings.LanguageType
import com.artkotlindev.domain.repository.local.SettingsRepository

class SetLanguageUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(language: LanguageType) = repository.saveLanguage(language = language)
}
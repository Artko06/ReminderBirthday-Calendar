package com.artkotlindev.domain.useCase.settings.language

import com.artkotlindev.domain.models.settings.LanguageType
import com.artkotlindev.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetLanguageUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<LanguageType> = repository.getLanguage()
}
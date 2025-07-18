package com.artkotlindev.domain.useCase.settings.theme

import com.artkotlindev.domain.models.settings.ThemeType
import com.artkotlindev.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetThemeUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<ThemeType> = repository.getTheme()
}
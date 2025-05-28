package com.example.domain.useCase.settings.theme

import com.example.domain.models.settings.ThemeType
import com.example.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetThemeUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<ThemeType> = repository.getTheme()
}
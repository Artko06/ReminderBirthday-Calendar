package com.example.domain.useCase.settings.theme

import com.example.domain.models.settings.ThemeType
import com.example.domain.repository.local.SettingsRepository

class SetThemeUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(theme: ThemeType) = repository.setTheme(theme = theme)
}
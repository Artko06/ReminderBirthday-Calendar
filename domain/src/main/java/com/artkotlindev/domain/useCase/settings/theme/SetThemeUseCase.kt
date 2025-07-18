package com.artkotlindev.domain.useCase.settings.theme

import com.artkotlindev.domain.models.settings.ThemeType
import com.artkotlindev.domain.repository.local.SettingsRepository

class SetThemeUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(theme: ThemeType) = repository.setTheme(theme = theme)
}
package com.example.domain.useCase.settings.firstLaunch

import com.example.domain.repository.SettingsRepository

class SetIsFirstLaunchUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(isFirstLaunch: Boolean) {
        repository.setIsFirstLaunch(isFirstLaunch = isFirstLaunch)
    }
}
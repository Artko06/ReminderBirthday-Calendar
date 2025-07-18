package com.artkotlindev.domain.useCase.settings.firstLaunch

import com.artkotlindev.domain.repository.local.SettingsRepository

class SetIsFirstLaunchUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(isFirstLaunch: Boolean) {
        repository.setIsFirstLaunch(isFirstLaunch = isFirstLaunch)
    }
}
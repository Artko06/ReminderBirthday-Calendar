package com.artkotlindev.domain.useCase.settings.firstLaunch

import com.artkotlindev.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetIsFirstLaunchUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.getIsFirstLaunch()
}
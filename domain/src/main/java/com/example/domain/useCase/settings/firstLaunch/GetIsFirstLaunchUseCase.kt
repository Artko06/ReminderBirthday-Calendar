package com.example.domain.useCase.settings.firstLaunch

import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetIsFirstLaunchUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.getIsFirstLaunch()
}
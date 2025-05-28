package com.example.domain.useCase.settings.viewDaysLeft

import com.example.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetStatusViewDaysLeftUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<Boolean> = repository.getStatusViewDaysLeft()
}
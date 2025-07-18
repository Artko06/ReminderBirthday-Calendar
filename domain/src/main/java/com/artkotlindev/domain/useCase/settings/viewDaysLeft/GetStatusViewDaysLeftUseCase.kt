package com.artkotlindev.domain.useCase.settings.viewDaysLeft

import com.artkotlindev.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetStatusViewDaysLeftUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<Boolean> = repository.getStatusViewDaysLeft()
}
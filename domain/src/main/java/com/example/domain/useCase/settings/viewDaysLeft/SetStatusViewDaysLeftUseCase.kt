package com.example.domain.useCase.settings.viewDaysLeft

import com.example.domain.repository.local.SettingsRepository

class SetStatusViewDaysLeftUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(activeStatus: Boolean) = repository.setStatusViewDaysLeft(activeStatus = activeStatus)
}
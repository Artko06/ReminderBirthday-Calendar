package com.artkotlindev.domain.useCase.settings.viewDaysLeft

import com.artkotlindev.domain.repository.local.SettingsRepository

class SetStatusViewDaysLeftUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(activeStatus: Boolean) = repository.setStatusViewDaysLeft(activeStatus = activeStatus)
}
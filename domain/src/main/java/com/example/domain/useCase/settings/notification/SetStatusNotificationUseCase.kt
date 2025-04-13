package com.example.domain.useCase.settings.notification

import com.example.domain.repository.SettingsRepository

class SetStatusNotificationUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(activeStatus: Boolean) = repository.setStatusNotification(activeStatus = activeStatus)
}
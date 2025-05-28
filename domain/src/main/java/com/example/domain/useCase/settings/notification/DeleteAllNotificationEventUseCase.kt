package com.example.domain.useCase.settings.notification

import com.example.domain.repository.local.SettingsRepository

class DeleteAllNotificationEventUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(): Int {
        return repository.deleteAllNotificationEvent()
    }
}
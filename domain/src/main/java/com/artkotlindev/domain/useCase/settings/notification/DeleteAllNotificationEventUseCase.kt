package com.artkotlindev.domain.useCase.settings.notification

import com.artkotlindev.domain.repository.local.SettingsRepository

class DeleteAllNotificationEventUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(): Int {
        return repository.deleteAllNotificationEvent()
    }
}
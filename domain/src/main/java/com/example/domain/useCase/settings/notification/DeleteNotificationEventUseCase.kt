package com.example.domain.useCase.settings.notification

import com.example.domain.models.notification.NotificationEvent
import com.example.domain.repository.local.SettingsRepository

class DeleteNotificationEventUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(notificationEvent: NotificationEvent): Boolean {
        return repository.deleteNotificationEvent(notificationEvent = notificationEvent)
    }
}
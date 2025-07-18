package com.artkotlindev.domain.useCase.settings.notification

import com.artkotlindev.domain.models.notification.NotificationEvent
import com.artkotlindev.domain.repository.local.SettingsRepository

class DeleteNotificationEventUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(notificationEvent: NotificationEvent): Boolean {
        return repository.deleteNotificationEvent(notificationEvent = notificationEvent)
    }
}
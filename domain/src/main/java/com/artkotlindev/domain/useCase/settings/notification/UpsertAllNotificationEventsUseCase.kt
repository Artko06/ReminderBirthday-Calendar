package com.artkotlindev.domain.useCase.settings.notification

import com.artkotlindev.domain.models.notification.NotificationEvent
import com.artkotlindev.domain.repository.local.SettingsRepository

class UpsertAllNotificationEventsUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(notificationEvents: List<NotificationEvent>) {
        repository.upsertNotificationEvents(notificationEvents = notificationEvents)
    }
}
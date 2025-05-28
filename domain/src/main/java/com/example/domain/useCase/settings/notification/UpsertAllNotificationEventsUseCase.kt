package com.example.domain.useCase.settings.notification

import com.example.domain.models.notification.NotificationEvent
import com.example.domain.repository.local.SettingsRepository

class UpsertAllNotificationEventsUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(notificationEvents: List<NotificationEvent>) {
        repository.upsertNotificationEvents(notificationEvents = notificationEvents)
    }
}
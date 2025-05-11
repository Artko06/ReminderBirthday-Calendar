package com.example.domain.useCase.settings.notification

import com.example.domain.models.notification.NotificationEvent
import com.example.domain.repository.SettingsRepository

class UpsertNotificationEventUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(hour: Int, minute: Int, daysBeforeEvent: Int, statusOn: Boolean) =
        repository.upsertNotificationEvent(
            notificationEvent = NotificationEvent(
                id = 0,
                hour = hour,
                minute = minute,
                daysBeforeEvent = daysBeforeEvent,
                statusOn = statusOn
            )
        )

    suspend operator fun invoke(notificationEvent: NotificationEvent) {
        repository.upsertNotificationEvent(notificationEvent = notificationEvent)
    }
}
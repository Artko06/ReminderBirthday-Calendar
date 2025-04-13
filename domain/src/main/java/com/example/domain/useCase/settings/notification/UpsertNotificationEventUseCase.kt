package com.example.domain.useCase.settings.notification

import com.example.domain.models.notification.NotificationEvent
import com.example.domain.repository.SettingsRepository

class UpsertNotificationEventUseCase(val repository: SettingsRepository) {
    suspend operator fun invoke(id: Int, hour: Int, minute: Int, daysBeforeEvent: Int) = repository.upsertNotificationEvent(
        notificationEvent = NotificationEvent(
            id = id,
            hour = hour,
            minute = minute,
            daysBeforeEvent = daysBeforeEvent
        )
    )
}
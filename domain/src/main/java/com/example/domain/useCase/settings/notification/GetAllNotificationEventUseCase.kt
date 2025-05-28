package com.example.domain.useCase.settings.notification

import com.example.domain.models.notification.NotificationEvent
import com.example.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotificationEventUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<List<NotificationEvent>> = repository.getAllNotificationEvent()
}
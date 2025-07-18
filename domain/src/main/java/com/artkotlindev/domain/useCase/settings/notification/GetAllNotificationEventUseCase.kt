package com.artkotlindev.domain.useCase.settings.notification

import com.artkotlindev.domain.models.notification.NotificationEvent
import com.artkotlindev.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotificationEventUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<List<NotificationEvent>> = repository.getAllNotificationEvent()
}
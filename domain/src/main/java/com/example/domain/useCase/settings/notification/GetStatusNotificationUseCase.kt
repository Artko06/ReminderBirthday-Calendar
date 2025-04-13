package com.example.domain.useCase.settings.notification

import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetStatusNotificationUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<Boolean> = repository.getStatusNotification()
}
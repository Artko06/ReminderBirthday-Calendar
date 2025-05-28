package com.example.domain.useCase.settings.showTypeEvent

import com.example.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetStatusShowBirthdayEventUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.getStatusShowBirthdayEvent()
}
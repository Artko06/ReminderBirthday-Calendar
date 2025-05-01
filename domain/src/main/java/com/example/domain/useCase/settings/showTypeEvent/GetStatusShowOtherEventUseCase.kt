package com.example.domain.useCase.settings.showTypeEvent

import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetStatusShowOtherEventUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.getStatusShowOtherEvent()
}
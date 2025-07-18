package com.artkotlindev.domain.useCase.settings.showTypeEvent

import com.artkotlindev.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetStatusShowOtherEventUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.getStatusShowOtherEvent()
}
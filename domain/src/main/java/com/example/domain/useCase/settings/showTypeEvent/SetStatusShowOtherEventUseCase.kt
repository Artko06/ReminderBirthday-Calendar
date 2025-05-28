package com.example.domain.useCase.settings.showTypeEvent

import com.example.domain.repository.local.SettingsRepository

class SetStatusShowOtherEventUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(activeStatus: Boolean) =
        repository.setStatusShowOtherEvent(activeStatus = activeStatus)
}
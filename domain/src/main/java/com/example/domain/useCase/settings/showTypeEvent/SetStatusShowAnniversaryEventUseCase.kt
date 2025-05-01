package com.example.domain.useCase.settings.showTypeEvent

import com.example.domain.repository.SettingsRepository

class SetStatusShowAnniversaryEventUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(activeStatus: Boolean) =
        repository.setStatusShowAnniversaryEvent(activeStatus = activeStatus)
}
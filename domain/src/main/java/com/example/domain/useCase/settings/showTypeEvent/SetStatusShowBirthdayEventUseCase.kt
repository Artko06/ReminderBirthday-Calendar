package com.example.domain.useCase.settings.showTypeEvent

import com.example.domain.repository.local.SettingsRepository

class SetStatusShowBirthdayEventUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(activeStatus: Boolean) =
        repository.setStatusShowBirthdayEvent(activeStatus = activeStatus)
}
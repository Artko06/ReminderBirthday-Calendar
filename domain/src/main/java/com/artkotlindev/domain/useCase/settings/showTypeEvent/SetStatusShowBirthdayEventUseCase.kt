package com.artkotlindev.domain.useCase.settings.showTypeEvent

import com.artkotlindev.domain.repository.local.SettingsRepository

class SetStatusShowBirthdayEventUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(activeStatus: Boolean) =
        repository.setStatusShowBirthdayEvent(activeStatus = activeStatus)
}
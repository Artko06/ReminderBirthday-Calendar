package com.artkotlindev.domain.useCase.settings.showTypeEvent

import com.artkotlindev.domain.repository.local.SettingsRepository

class SetStatusShowAnniversaryEventUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(activeStatus: Boolean) =
        repository.setStatusShowAnniversaryEvent(activeStatus = activeStatus)
}
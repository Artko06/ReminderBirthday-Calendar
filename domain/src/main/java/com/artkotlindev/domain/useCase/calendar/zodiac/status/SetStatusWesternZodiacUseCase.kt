package com.artkotlindev.domain.useCase.calendar.zodiac.status

import com.artkotlindev.domain.repository.local.SettingsRepository

class SetStatusWesternZodiacUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(activeStatus: Boolean) = repository.setStatusZodiacSign(activeStatus = activeStatus)
}
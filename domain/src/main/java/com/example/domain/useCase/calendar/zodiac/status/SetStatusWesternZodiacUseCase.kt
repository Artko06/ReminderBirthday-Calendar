package com.example.domain.useCase.calendar.zodiac.status

import com.example.domain.repository.local.SettingsRepository

class SetStatusWesternZodiacUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(activeStatus: Boolean) = repository.setStatusZodiacSign(activeStatus = activeStatus)
}
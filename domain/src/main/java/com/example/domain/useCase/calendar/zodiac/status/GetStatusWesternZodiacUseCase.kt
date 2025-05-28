package com.example.domain.useCase.calendar.zodiac.status

import com.example.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetStatusWesternZodiacUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<Boolean> = repository.getStatusZodiacSign()
}
package com.artkotlindev.domain.useCase.calendar.zodiac.status

import com.artkotlindev.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetStatusChineseZodiacUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<Boolean> = repository.getStatusZodiacChinese()
}
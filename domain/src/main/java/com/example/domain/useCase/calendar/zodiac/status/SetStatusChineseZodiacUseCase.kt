package com.example.domain.useCase.calendar.zodiac.status

import com.example.domain.repository.local.SettingsRepository

class SetStatusChineseZodiacUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(activeStatus: Boolean) = repository.setStatusZodiacChinese(activeStatus = activeStatus)
}
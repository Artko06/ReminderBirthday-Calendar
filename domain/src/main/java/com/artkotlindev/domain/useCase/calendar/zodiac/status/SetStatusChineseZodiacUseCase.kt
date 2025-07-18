package com.artkotlindev.domain.useCase.calendar.zodiac.status

import com.artkotlindev.domain.repository.local.SettingsRepository

class SetStatusChineseZodiacUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(activeStatus: Boolean) = repository.setStatusZodiacChinese(activeStatus = activeStatus)
}
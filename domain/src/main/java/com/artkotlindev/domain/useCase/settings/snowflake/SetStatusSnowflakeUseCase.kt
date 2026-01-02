package com.artkotlindev.domain.useCase.settings.snowflake

import com.artkotlindev.domain.repository.local.SettingsRepository

class SetStatusSnowflakeUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(statusSnowflake: Boolean) = settingsRepository.setStatusSnowflake(activeStatus = statusSnowflake)
}
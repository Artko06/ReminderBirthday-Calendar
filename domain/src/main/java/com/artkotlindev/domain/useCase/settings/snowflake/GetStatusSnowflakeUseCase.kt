package com.artkotlindev.domain.useCase.settings.snowflake

import com.artkotlindev.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetStatusSnowflakeUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke(): Flow<Boolean> = settingsRepository.getStatusSnowflake()
}
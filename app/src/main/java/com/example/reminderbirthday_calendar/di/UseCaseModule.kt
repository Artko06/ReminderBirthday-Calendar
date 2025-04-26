package com.example.reminderbirthday_calendar.di

import com.example.domain.repository.SettingsRepository
import com.example.domain.useCase.settings.firstLaunch.GetIsFirstLaunchUseCase
import com.example.domain.useCase.settings.firstLaunch.SetIsFirstLaunchUseCase
import com.example.domain.useCase.settings.notification.GetStatusNotificationUseCase
import com.example.domain.useCase.settings.notification.SetStatusNotificationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetIsFirstLaunchUseCase(settingsRepository: SettingsRepository): GetIsFirstLaunchUseCase{
        return GetIsFirstLaunchUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSetIsFirstLaunchUseCase(settingsRepository: SettingsRepository): SetIsFirstLaunchUseCase{
        return SetIsFirstLaunchUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideGetStatusNotificationUseCase(settingsRepository: SettingsRepository): GetStatusNotificationUseCase{
        return GetStatusNotificationUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSetStatusNotificationUseCase(settingsRepository: SettingsRepository): SetStatusNotificationUseCase{
        return SetStatusNotificationUseCase(repository = settingsRepository)
    }
}
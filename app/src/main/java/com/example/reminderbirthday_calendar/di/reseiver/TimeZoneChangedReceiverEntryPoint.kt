package com.example.reminderbirthday_calendar.di.reseiver

import com.example.domain.useCase.settings.notification.ScheduleAllEventsUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface TimeZoneChangedReceiverEntryPoint {
    fun scheduleAllEventsUseCase(): ScheduleAllEventsUseCase
}
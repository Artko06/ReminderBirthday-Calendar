package com.artkotlindev.reminderbirthday_calendar.di.reseiver

import com.artkotlindev.domain.useCase.settings.notification.ScheduleAllEventsUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DateTimeChangedReceiverEntryPoint {
    fun scheduleAllEventsUseCase(): ScheduleAllEventsUseCase
}
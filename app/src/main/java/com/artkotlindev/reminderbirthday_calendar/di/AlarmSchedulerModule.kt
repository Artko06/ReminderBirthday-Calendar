package com.artkotlindev.reminderbirthday_calendar.di

import android.content.Context
import com.artkotlindev.domain.repository.local.AlarmEventScheduler
import com.artkotlindev.reminderbirthday_calendar.notification.schedulerImpl.AlarmEventSchedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AlarmSchedulerModule {

    @Provides
    fun provideAlarmEventScheduler(@ApplicationContext context : Context): AlarmEventScheduler{
        return AlarmEventSchedulerImpl(
            context = context
        )
    }
}
package com.example.reminderbirthday_calendar.di

import android.content.Context
import com.example.data.local.util.zodiac.ZodiacCalculatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ZodiacModule {

    @Provides
    fun provideContextToZodiac(@ApplicationContext context: Context): ZodiacCalculatorImpl {
        return ZodiacCalculatorImpl(context = context)
    }
}
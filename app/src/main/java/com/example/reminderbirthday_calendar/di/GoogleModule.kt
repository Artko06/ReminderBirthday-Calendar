package com.example.reminderbirthday_calendar.di

import android.content.Context
import com.example.data.local.util.google.GoogleAuthClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GoogleModule {

    @Provides
    fun provideGoogleAuthClient(@ApplicationContext context: Context): GoogleAuthClient{
        return GoogleAuthClient(context = context)
    }
}
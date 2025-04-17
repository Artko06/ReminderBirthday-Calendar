package com.example.reminderbirthday_calendar.di

import android.content.ContentResolver
import android.content.Context
import com.example.data.local.roomDb.dao.EventDao
import com.example.data.local.roomDb.dao.SettingsDao
import com.example.data.local.util.google.GoogleAuthClient
import com.example.data.repository.ContactAppRepositoryImpl
import com.example.data.repository.EventRepositoryImpl
import com.example.data.repository.ExportFileRepositoryImpl
import com.example.data.repository.GoogleClientRepositoryImpl
import com.example.data.repository.SettingsRepositoryImpl
import com.example.domain.repository.ContactAppRepository
import com.example.domain.repository.EventRepository
import com.example.domain.repository.ExportFileRepository
import com.example.domain.repository.GoogleClientRepository
import com.example.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    fun provideContactAppRepository(contentResolver: ContentResolver): ContactAppRepository {
        return ContactAppRepositoryImpl(contentResolver = contentResolver)
    }

    @Provides
    fun provideEventRepository(eventDao: EventDao): EventRepository {
        return EventRepositoryImpl(eventDao = eventDao)
    }

    @Provides
    fun provideSettingsRepository(
        @ApplicationContext context: Context,
        settingsDao: SettingsDao
    ): SettingsRepository {
        return SettingsRepositoryImpl(
            context = context,
            settingsDao = settingsDao
        )
    }

    @Provides
    fun provideExportFileRepository(
        @ApplicationContext context: Context,
        eventDao: EventDao
    ): ExportFileRepository{
        return ExportFileRepositoryImpl(
            context = context,
            eventDao = eventDao
        )
    }

    @Provides
    fun provideGoogleClientRepository(googleAuthClient: GoogleAuthClient): GoogleClientRepository{
        return GoogleClientRepositoryImpl(googleAuthClient = googleAuthClient)
    }
}
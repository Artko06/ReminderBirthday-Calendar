package com.example.reminderbirthday_calendar.di

import android.content.ContentResolver
import android.content.Context
import com.example.data.local.roomDb.dao.EventDao
import com.example.data.local.roomDb.dao.SettingsDao
import com.example.data.remote.util.google.GoogleAuthClient
import com.example.data.remote.util.google.GoogleDriveClient
import com.example.data.local.repository.ContactAppRepositoryImpl
import com.example.data.local.repository.EventRepositoryImpl
import com.example.data.local.repository.ExportFileRepositoryImpl
import com.example.data.remote.repository.GoogleClientRepositoryImpl
import com.example.data.local.repository.ImportFileRepositoryImpl
import com.example.data.local.repository.SettingsRepositoryImpl
import com.example.domain.repository.local.ContactAppRepository
import com.example.domain.repository.local.EventRepository
import com.example.domain.repository.local.ExportFileRepository
import com.example.domain.repository.remote.GoogleClientRepository
import com.example.domain.repository.local.ImportFileRepository
import com.example.domain.repository.local.SettingsRepository
import com.example.domain.util.zodiac.ZodiacCalculator
import com.example.domain.util.zodiac.ZodiacCalculatorImpl
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
    fun provideImportFileRepository(
        @ApplicationContext context: Context
    ): ImportFileRepository{
        return ImportFileRepositoryImpl(
            context = context
        )
    }

    @Provides
    fun provideGoogleClientRepository(
        googleAuthClient: GoogleAuthClient,
        googleDriveClient: GoogleDriveClient
    ): GoogleClientRepository{
        return GoogleClientRepositoryImpl(
            googleAuthClient = googleAuthClient,
            googleDriveClient = googleDriveClient
        )
    }

    @Provides
    fun provideZodiacCalculator(): ZodiacCalculator{
        return ZodiacCalculatorImpl()
    }
}
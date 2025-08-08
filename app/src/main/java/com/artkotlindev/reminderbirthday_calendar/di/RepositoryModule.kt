package com.artkotlindev.reminderbirthday_calendar.di

import android.content.ContentResolver
import android.content.Context
import com.artkotlindev.data.local.repository.ContactAppRepositoryImpl
import com.artkotlindev.data.local.repository.EventRepositoryImpl
import com.artkotlindev.data.local.repository.ExportFileRepositoryImpl
import com.artkotlindev.data.local.repository.ImportFileRepositoryImpl
import com.artkotlindev.data.local.repository.SettingsRepositoryImpl
import com.artkotlindev.data.local.roomDb.dao.EventDao
import com.artkotlindev.data.local.roomDb.dao.SettingsDao
import com.artkotlindev.domain.repository.local.ContactAppRepository
import com.artkotlindev.domain.repository.local.EventRepository
import com.artkotlindev.domain.repository.local.ExportFileRepository
import com.artkotlindev.domain.repository.local.ImportFileRepository
import com.artkotlindev.domain.repository.local.SettingsRepository
import com.artkotlindev.domain.util.zodiac.ZodiacCalculator
import com.artkotlindev.domain.util.zodiac.ZodiacCalculatorImpl
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
    fun provideZodiacCalculator(): ZodiacCalculator{
        return ZodiacCalculatorImpl()
    }
}
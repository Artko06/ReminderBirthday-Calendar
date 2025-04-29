package com.example.reminderbirthday_calendar.di

import com.example.domain.repository.ContactAppRepository
import com.example.domain.repository.EventRepository
import com.example.domain.repository.ExportFileRepository
import com.example.domain.repository.ImportFileRepository
import com.example.domain.repository.SettingsRepository
import com.example.domain.useCase.calendar.event.DeleteEventsUseCase
import com.example.domain.useCase.calendar.event.GetAllEventUseCase
import com.example.domain.useCase.calendar.event.GetEventByContactNameUseCase
import com.example.domain.useCase.calendar.event.GetEventByTypeUseCase
import com.example.domain.useCase.calendar.event.ImportEventsFromContactsUseCase
import com.example.domain.useCase.calendar.event.UpsertEventUseCase
import com.example.domain.useCase.calendar.event.UpsertEventsUseCase
import com.example.domain.useCase.exportFile.ExportEventsToCsvToExternalDirUseCase
import com.example.domain.useCase.exportFile.ExportEventsToJsonToExternalDirUseCase
import com.example.domain.useCase.importFile.ImportEventsFromCsvUseCase
import com.example.domain.useCase.importFile.ImportEventsFromJsonUseCase
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
    fun provideGetIsFirstLaunchUseCase(settingsRepository: SettingsRepository): GetIsFirstLaunchUseCase {
        return GetIsFirstLaunchUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSetIsFirstLaunchUseCase(settingsRepository: SettingsRepository): SetIsFirstLaunchUseCase {
        return SetIsFirstLaunchUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideGetStatusNotificationUseCase(settingsRepository: SettingsRepository): GetStatusNotificationUseCase {
        return GetStatusNotificationUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSetStatusNotificationUseCase(settingsRepository: SettingsRepository): SetStatusNotificationUseCase {
        return SetStatusNotificationUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideImportEventsFromContactsUseCase(contactAppRepository: ContactAppRepository): ImportEventsFromContactsUseCase {
        return ImportEventsFromContactsUseCase(repository = contactAppRepository)
    }

    @Provides
    @Singleton
    fun provideGetEventByContactNameUseCase(eventRepository: EventRepository): GetEventByContactNameUseCase {
        return GetEventByContactNameUseCase(repository = eventRepository)
    }

    @Provides
    @Singleton
    fun provideUpsertEventsUseCase(eventRepository: EventRepository): UpsertEventsUseCase {
        return UpsertEventsUseCase(repository = eventRepository)
    }

    @Provides
    @Singleton
    fun provideUpsertEventUseCase(eventRepository: EventRepository): UpsertEventUseCase {
        return UpsertEventUseCase(repository = eventRepository)
    }

    @Provides
    @Singleton
    fun provideGetEventByTypeUseCase(eventRepository: EventRepository): GetEventByTypeUseCase {
        return GetEventByTypeUseCase(repository = eventRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllEventUseCase(eventRepository: EventRepository): GetAllEventUseCase {
        return GetAllEventUseCase(repository = eventRepository)
    }

    @Provides
    @Singleton
    fun provideExportEventsToCsvToExternalDirUseCase(fileExportRepository: ExportFileRepository): ExportEventsToCsvToExternalDirUseCase {
        return ExportEventsToCsvToExternalDirUseCase(repository = fileExportRepository)
    }

    @Provides
    @Singleton
    fun provideExportEventsToJsonToExternalDirUseCase(fileExportRepository: ExportFileRepository): ExportEventsToJsonToExternalDirUseCase {
        return ExportEventsToJsonToExternalDirUseCase(repository = fileExportRepository)
    }

    @Provides
    @Singleton
    fun provideImportEventsFromJsonUseCase(fileImportRepository: ImportFileRepository): ImportEventsFromJsonUseCase{
        return ImportEventsFromJsonUseCase(repository = fileImportRepository)
    }

    @Provides
    @Singleton
    fun provideImportEventsFromCsvUseCase(fileImportRepository: ImportFileRepository): ImportEventsFromCsvUseCase{
        return ImportEventsFromCsvUseCase(repository = fileImportRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteEventsUseCase(eventRepository: EventRepository): DeleteEventsUseCase{
        return DeleteEventsUseCase(repository = eventRepository)
    }
}
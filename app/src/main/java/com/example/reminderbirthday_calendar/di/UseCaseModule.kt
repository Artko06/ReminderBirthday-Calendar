package com.example.reminderbirthday_calendar.di

import com.example.domain.repository.AlarmEventScheduler
import com.example.domain.repository.ContactAppRepository
import com.example.domain.repository.EventRepository
import com.example.domain.repository.ExportFileRepository
import com.example.domain.repository.GoogleClientRepository
import com.example.domain.repository.ImportFileRepository
import com.example.domain.repository.SettingsRepository
import com.example.domain.useCase.calendar.contact.ImportContactsUseCase
import com.example.domain.useCase.calendar.event.DeleteAllEventsUseCase
import com.example.domain.useCase.calendar.event.DeleteEventUseCase
import com.example.domain.useCase.calendar.event.DeleteEventsUseCase
import com.example.domain.useCase.calendar.event.GetAllEventUseCase
import com.example.domain.useCase.calendar.event.GetEventByContactNameUseCase
import com.example.domain.useCase.calendar.event.GetEventByIdUseCase
import com.example.domain.useCase.calendar.event.GetEventByTypeUseCase
import com.example.domain.useCase.calendar.event.GetEventsByDateUseCase
import com.example.domain.useCase.calendar.event.GetEventsByMonthUseCase
import com.example.domain.useCase.calendar.event.GetEventsBySortTypeUseCase
import com.example.domain.useCase.calendar.event.ImportEventsFromContactsUseCase
import com.example.domain.useCase.calendar.event.UpsertEventUseCase
import com.example.domain.useCase.calendar.event.UpsertEventsUseCase
import com.example.domain.useCase.calendar.zodiac.GetChineseZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.GetWesternZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.status.GetStatusChineseZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.status.GetStatusWesternZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.status.SetStatusChineseZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.status.SetStatusWesternZodiacUseCase
import com.example.domain.useCase.exportFile.ExportEventsToCsvToExternalDirUseCase
import com.example.domain.useCase.exportFile.ExportEventsToJsonToExternalDirUseCase
import com.example.domain.useCase.google.GetAuthGoogleEmailUseCase
import com.example.domain.useCase.google.GetEventsFromRemoteUseCase
import com.example.domain.useCase.google.GetTimeLastUploadToRemoteUseCase
import com.example.domain.useCase.google.GoogleIsSignInUseCase
import com.example.domain.useCase.google.GoogleSignInUseCase
import com.example.domain.useCase.google.GoogleSignOutUseCase
import com.example.domain.useCase.google.UploadEventsToRemoteUseCase
import com.example.domain.useCase.importFile.ImportEventsFromCsvUseCase
import com.example.domain.useCase.importFile.ImportEventsFromJsonUseCase
import com.example.domain.useCase.settings.firstLaunch.GetIsFirstLaunchUseCase
import com.example.domain.useCase.settings.firstLaunch.SetIsFirstLaunchUseCase
import com.example.domain.useCase.settings.notification.CancelNotifyAllEventUseCase
import com.example.domain.useCase.settings.notification.DeleteAllNotificationEventUseCase
import com.example.domain.useCase.settings.notification.DeleteNotificationEventUseCase
import com.example.domain.useCase.settings.notification.GetAllNotificationEventUseCase
import com.example.domain.useCase.settings.notification.ScheduleAlarmItemUseCase
import com.example.domain.useCase.settings.notification.ScheduleAllEventsUseCase
import com.example.domain.useCase.settings.notification.UpsertAllNotificationEventsUseCase
import com.example.domain.useCase.settings.notification.UpsertNotificationEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.GetStatusShowAnniversaryEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.GetStatusShowBirthdayEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.GetStatusShowOtherEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.SetStatusShowAnniversaryEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.SetStatusShowBirthdayEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.SetStatusShowOtherEventUseCase
import com.example.domain.useCase.settings.theme.GetThemeUseCase
import com.example.domain.useCase.settings.theme.SetThemeUseCase
import com.example.domain.useCase.settings.viewDaysLeft.GetStatusViewDaysLeftUseCase
import com.example.domain.useCase.settings.viewDaysLeft.SetStatusViewDaysLeftUseCase
import com.example.domain.util.zodiac.ZodiacCalculator
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
    fun provideGetStatusNotificationUseCase(settingsRepository: SettingsRepository): GetStatusViewDaysLeftUseCase {
        return GetStatusViewDaysLeftUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSetStatusNotificationUseCase(settingsRepository: SettingsRepository): SetStatusViewDaysLeftUseCase {
        return SetStatusViewDaysLeftUseCase(repository = settingsRepository)
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
    fun provideGetEventsBySortTypeUseCase(eventRepository: EventRepository): GetEventsBySortTypeUseCase{
        return GetEventsBySortTypeUseCase(repository = eventRepository)
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

    @Provides
    @Singleton
    fun provideDeleteAllEventsUseCase(eventRepository: EventRepository): DeleteAllEventsUseCase{
        return DeleteAllEventsUseCase(repository = eventRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteEventUseCase(eventRepository: EventRepository): DeleteEventUseCase{
        return DeleteEventUseCase(repository = eventRepository)
    }

    @Provides
    @Singleton
    fun provideGetStatusChineseZodiacUseCase(settingsRepository: SettingsRepository): GetStatusChineseZodiacUseCase{
        return GetStatusChineseZodiacUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSetStatusChineseZodiacUseCase(settingsRepository: SettingsRepository): SetStatusChineseZodiacUseCase{
        return SetStatusChineseZodiacUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideGetStatusWesternZodiacUseCase(settingsRepository: SettingsRepository): GetStatusWesternZodiacUseCase{
        return GetStatusWesternZodiacUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSetStatusWesternZodiacUseCase(settingsRepository: SettingsRepository): SetStatusWesternZodiacUseCase{
        return SetStatusWesternZodiacUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideGoogleIsSignInUseCase(googleClientRepository: GoogleClientRepository): GoogleIsSignInUseCase{
        return GoogleIsSignInUseCase(repository = googleClientRepository)
    }

    @Provides
    @Singleton
    fun provideGoogleSignInUseCase(googleClientRepository: GoogleClientRepository): GoogleSignInUseCase{
        return GoogleSignInUseCase(repository = googleClientRepository)
    }

    @Provides
    @Singleton
    fun provideGoogleSignOutUseCase(googleClientRepository: GoogleClientRepository): GoogleSignOutUseCase{
        return GoogleSignOutUseCase(repository = googleClientRepository)
    }

    @Provides
    @Singleton
    fun provideGetAuthGoogleEmailUseCase(googleClientRepository: GoogleClientRepository): GetAuthGoogleEmailUseCase{
       return GetAuthGoogleEmailUseCase(repository = googleClientRepository)
    }

    @Provides
    @Singleton
    fun provideGetEventsFromRemoteUseCase(googleClientRepository: GoogleClientRepository): GetEventsFromRemoteUseCase{
        return GetEventsFromRemoteUseCase(repository = googleClientRepository)
    }

    @Provides
    @Singleton
    fun provideGetTimeLastUploadToRemoteUseCase(googleClientRepository: GoogleClientRepository): GetTimeLastUploadToRemoteUseCase{
        return GetTimeLastUploadToRemoteUseCase(repository = googleClientRepository)
    }

    @Provides
    @Singleton
    fun provideUploadEventsToRemoteUseCase(googleClientRepository: GoogleClientRepository): UploadEventsToRemoteUseCase{
        return UploadEventsToRemoteUseCase(repository = googleClientRepository)
    }

    @Provides
    @Singleton
    fun provideGetStatusShowAnniversaryEventUseCase(settingsRepository: SettingsRepository): GetStatusShowAnniversaryEventUseCase{
        return GetStatusShowAnniversaryEventUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideGetStatusShowBirthdayEventUseCase(settingsRepository: SettingsRepository): GetStatusShowBirthdayEventUseCase{
        return GetStatusShowBirthdayEventUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideGetStatusShowOtherEventUseCase(settingsRepository: SettingsRepository): GetStatusShowOtherEventUseCase{
        return GetStatusShowOtherEventUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSetStatusShowAnniversaryEventUseCase(settingsRepository: SettingsRepository): SetStatusShowAnniversaryEventUseCase{
        return SetStatusShowAnniversaryEventUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSetStatusShowBirthdayEventUseCase(settingsRepository: SettingsRepository): SetStatusShowBirthdayEventUseCase{
        return SetStatusShowBirthdayEventUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSetStatusShowOtherEventUseCase(settingsRepository: SettingsRepository): SetStatusShowOtherEventUseCase{
        return SetStatusShowOtherEventUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideScheduleAllEventsUseCase(
        alarmEventScheduler: AlarmEventScheduler,
        eventRepository: EventRepository,
        settingsRepository: SettingsRepository
    ): ScheduleAllEventsUseCase{
        return ScheduleAllEventsUseCase(
            alarmRepository = alarmEventScheduler,
            eventRepository = eventRepository,
            settingsRepository = settingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideCancelNotifyAllEventUseCase(
        alarmEventScheduler: AlarmEventScheduler,
        eventRepository: EventRepository,
        settingsRepository: SettingsRepository
    ): CancelNotifyAllEventUseCase{
        return CancelNotifyAllEventUseCase(
            alarmRepository = alarmEventScheduler,
            eventRepository = eventRepository,
            settingsRepository = settingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideScheduleAlarmItemUseCase(
        alarmEventScheduler: AlarmEventScheduler
    ): ScheduleAlarmItemUseCase{
        return ScheduleAlarmItemUseCase(
            scheduler = alarmEventScheduler
        )
    }

    @Provides
    @Singleton
    fun provideGetAllNotificationEventUseCase(settingsRepository: SettingsRepository): GetAllNotificationEventUseCase{
        return GetAllNotificationEventUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideUpsertNotificationEventUseCase(settingsRepository: SettingsRepository): UpsertNotificationEventUseCase{
        return UpsertNotificationEventUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideUpsertAllNotificationEventsUseCase(settingsRepository: SettingsRepository): UpsertAllNotificationEventsUseCase{
        return UpsertAllNotificationEventsUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteNotificationEventUseCase(settingsRepository: SettingsRepository): DeleteNotificationEventUseCase{
        return DeleteNotificationEventUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteAllNotificationEventUseCase(settingsRepository: SettingsRepository): DeleteAllNotificationEventUseCase{
        return DeleteAllNotificationEventUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideImportContactsUseCase(contactAppRepository: ContactAppRepository): ImportContactsUseCase{
        return ImportContactsUseCase(repository = contactAppRepository)
    }

    @Provides
    @Singleton
    fun provideGetThemeUseCase(settingsRepository: SettingsRepository): GetThemeUseCase{
        return GetThemeUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSetThemeUseCase(settingsRepository: SettingsRepository): SetThemeUseCase{
        return SetThemeUseCase(repository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideGetEventsByDateUseCase() = GetEventsByDateUseCase()

    @Provides
    @Singleton
    fun provideGetEventsByMonthUseCase() = GetEventsByMonthUseCase()

    @Provides
    @Singleton
    fun provideGetEventByIdUseCase(eventRepository: EventRepository): GetEventByIdUseCase{
        return GetEventByIdUseCase(repository = eventRepository)
    }

    @Provides
    @Singleton
    fun provideGetChineseZodiacUseCase(zodiacCalculator: ZodiacCalculator): GetChineseZodiacUseCase {
        return GetChineseZodiacUseCase(zodiacCalculator = zodiacCalculator)
    }

    @Provides
    @Singleton
    fun provideGetWesternZodiacUseCase(zodiacCalculator: ZodiacCalculator): GetWesternZodiacUseCase {
        return GetWesternZodiacUseCase(zodiacCalculator = zodiacCalculator)
    }
}
package com.example.data.repository

import android.content.Context
import com.example.data.local.entity.settings.notification.toData
import com.example.data.local.entity.settings.notification.toDomain
import com.example.data.local.preferences.PreferencesDataStore
import com.example.data.local.roomDb.dao.SettingsDao
import com.example.domain.models.notification.NotificationEvent
import com.example.domain.models.settings.LanguageType
import com.example.domain.models.settings.ThemeType
import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val context: Context,
    private val settingsDao: SettingsDao
): SettingsRepository
{
    // Language
    override fun getLanguage(): Flow<LanguageType> {
        return PreferencesDataStore.getLanguage(context = context)
    }

    override suspend fun saveLanguage(language: LanguageType) {
        PreferencesDataStore.setLanguage(context = context, language = language)
    }

    // Theme
    override fun getTheme(): Flow<ThemeType> {
        return PreferencesDataStore.getTheme(context = context)
    }

    override suspend fun setTheme(theme: ThemeType) {
        PreferencesDataStore.setTheme(context = context, theme = theme)
    }

    // Notification
    override fun getStatusNotification(): Flow<Boolean> {
        return PreferencesDataStore.getStatusNotification(context = context)
    }

    override suspend fun setStatusNotification(activeStatus: Boolean) {
        PreferencesDataStore.setStatusNotification(context = context, activeStatus = activeStatus)
    }

    override fun getAllNotificationEvent(): Flow<List<NotificationEvent>> {
        return settingsDao.getAllNotificationEvent().map { events ->
            events.map { event ->
                event.toDomain()
            }
        }
    }

    override suspend fun upsertNotificationEvent(notificationEvent: NotificationEvent) {
        settingsDao.upsertNotificationEvent(notificationEvent = notificationEvent.toData())
    }

    // Zodiac
    override fun getStatusZodiacWestern(): Flow<Boolean> {
        return PreferencesDataStore.getStatusZodiacWestern(context = context)
    }

    override suspend fun setStatusZodiacWestern(activeStatus: Boolean) {
        PreferencesDataStore.setStatusZodiacWestern(context = context, activeStatus = activeStatus)
    }

    override fun getStatusZodiacChinese(): Flow<Boolean> {
        return PreferencesDataStore.getStatusZodiacChinese(context = context)
    }

    override suspend fun setStatusZodiacChinese(activeStatus: Boolean) {
        PreferencesDataStore.setStatusZodiacChinese(context = context, activeStatus = activeStatus)
    }

    // First launch
    override fun getIsFirstLaunch(): Flow<Boolean> {
        return PreferencesDataStore.getIsFirstLaunch(context = context)
    }

    override suspend fun setIsFirstLaunch(isFirstLaunch: Boolean) {
        PreferencesDataStore.setIsFirstLaunch(context = context, isFirstLaunch = isFirstLaunch)
    }
}
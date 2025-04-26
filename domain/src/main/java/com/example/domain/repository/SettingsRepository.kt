package com.example.domain.repository

import com.example.domain.models.notification.NotificationEvent
import com.example.domain.models.settings.LanguageType
import com.example.domain.models.settings.ThemeType
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    // Language
    fun getLanguage(): Flow<LanguageType>
    suspend fun saveLanguage(language: LanguageType)

    // Theme
    fun getTheme(): Flow<ThemeType>
    suspend fun setTheme(theme: ThemeType)

    // Notification
    fun getStatusNotification(): Flow<Boolean>
    suspend fun setStatusNotification(activeStatus: Boolean)
    fun getAllNotificationEvent(): Flow<List<NotificationEvent>>
    suspend fun upsertNotificationEvent(notificationEvent: NotificationEvent)

    // Zodiac
    fun getStatusZodiacWestern(): Flow<Boolean>
    suspend fun setStatusZodiacWestern(activeStatus: Boolean)
    fun getStatusZodiacChinese(): Flow<Boolean>
    suspend fun setStatusZodiacChinese(activeStatus: Boolean)

    // First launch
    fun getIsFirstLaunch(): Flow<Boolean>
    suspend fun setIsFirstLaunch(isFirstLaunch: Boolean)
}
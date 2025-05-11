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

    // View days left
    fun getStatusViewDaysLeft(): Flow<Boolean>
    suspend fun setStatusViewDaysLeft(activeStatus: Boolean)

    // Show type event
    fun getStatusShowBirthdayEvent(): Flow<Boolean>
    suspend fun setStatusShowBirthdayEvent(activeStatus: Boolean)
    fun getStatusShowAnniversaryEvent(): Flow<Boolean>
    suspend fun setStatusShowAnniversaryEvent(activeStatus: Boolean)
    fun getStatusShowOtherEvent(): Flow<Boolean>
    suspend fun setStatusShowOtherEvent(activeStatus: Boolean)

    // Notification
    fun getAllNotificationEvent(): Flow<List<NotificationEvent>>
    suspend fun upsertNotificationEvent(notificationEvent: NotificationEvent): Boolean
    suspend fun upsertNotificationEvents(notificationEvents: List<NotificationEvent>): List<Boolean>
    suspend fun deleteNotificationEvent(notificationEvent: NotificationEvent): Boolean
    suspend fun deleteAllNotificationEvent(): Int

    // Zodiac
    fun getStatusZodiacWestern(): Flow<Boolean>
    suspend fun setStatusZodiacWestern(activeStatus: Boolean)
    fun getStatusZodiacChinese(): Flow<Boolean>
    suspend fun setStatusZodiacChinese(activeStatus: Boolean)

    // First launch
    fun getIsFirstLaunch(): Flow<Boolean>
    suspend fun setIsFirstLaunch(isFirstLaunch: Boolean)
}
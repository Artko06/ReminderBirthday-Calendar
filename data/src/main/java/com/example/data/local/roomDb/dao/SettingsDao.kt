package com.example.data.local.roomDb.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.local.entity.settings.notification.NotificationEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao{
    // Notification
    @Query("Select * FROM notification_event")
    fun getAllNotificationEvent(): Flow<List<NotificationEventEntity>>

    @Upsert
    suspend fun upsertNotificationEvent(notificationEvent: NotificationEventEntity)
}
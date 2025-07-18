package com.artkotlindev.data.local.roomDb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.artkotlindev.data.local.entity.settings.notification.NotificationEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    // Notification
    @Query("Select * FROM notification_event")
    fun getAllNotificationEvent(): Flow<List<NotificationEventEntity>>

    @Upsert
    suspend fun upsertNotificationEvent(notificationEvent: NotificationEventEntity): Long

    @Upsert
    suspend fun upsertNotificationEvents(notificationEvents: List<NotificationEventEntity>): List<Long>

    @Delete
    suspend fun deleteNotificationEvent(notificationEvent: NotificationEventEntity): Int

    @Query("DELETE FROM notification_event")
    suspend fun deleteAllNotificationEvent(): Int

    @Query("DELETE FROM sqlite_sequence WHERE name = 'notification_event'")
    suspend fun resetNotificationAutoIncrement()
}
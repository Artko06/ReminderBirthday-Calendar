package com.example.data.local.entity.settings.notification

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.notification.NotificationEvent

@Entity(tableName = "notification_event")
data class NotificationEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val hour: Int,
    val minute: Int,
    val daysBeforeEvent: Int
)


fun NotificationEventEntity.toDomain() = NotificationEvent(
    id = id,
    hour = hour,
    minute = minute,
    daysBeforeEvent = daysBeforeEvent
)

fun NotificationEvent.toData() = NotificationEventEntity(
    id = id,
    hour = hour,
    minute = minute,
    daysBeforeEvent = daysBeforeEvent
)

package com.artkotlindev.data.local.entity.settings.notification

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artkotlindev.domain.models.notification.NotificationEvent

@Entity(tableName = "notification_event")
data class NotificationEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val hour: Int,
    val minute: Int,
    val daysBeforeEvent: Int,
    val statusOn: Boolean
)


fun NotificationEventEntity.toDomain() = NotificationEvent(
    id = id,
    hour = hour,
    minute = minute,
    daysBeforeEvent = daysBeforeEvent,
    statusOn = statusOn
)

fun NotificationEvent.toData() = NotificationEventEntity(
    id = id,
    hour = hour,
    minute = minute,
    daysBeforeEvent = daysBeforeEvent,
    statusOn = statusOn
)

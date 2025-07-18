package com.artkotlindev.data.local.roomDb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artkotlindev.data.local.entity.event.EventEntity
import com.artkotlindev.data.local.entity.settings.notification.NotificationEventEntity
import com.artkotlindev.data.local.roomDb.dao.EventDao
import com.artkotlindev.data.local.roomDb.dao.SettingsDao

@Database(
    entities = [
        NotificationEventEntity::class,
        EventEntity::class
               ],
    version = 1
)
abstract class CalendarInfoDB: RoomDatabase() {
    abstract val settingsDao: SettingsDao
    abstract val eventDao: EventDao

    companion object{
        const val DATABASE_NAME = "CALENDAR_INFO"
    }
}
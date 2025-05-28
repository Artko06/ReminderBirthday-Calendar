package com.example.data.local.roomDb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.entity.event.EventEntity
import com.example.data.local.entity.settings.notification.NotificationEventEntity
import com.example.data.local.roomDb.dao.EventDao
import com.example.data.local.roomDb.dao.SettingsDao

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
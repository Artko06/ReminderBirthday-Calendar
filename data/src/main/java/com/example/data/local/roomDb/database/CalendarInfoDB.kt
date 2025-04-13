package com.example.data.local.roomDb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.entity.settings.notification.NotificationEventEntity
import com.example.data.local.roomDb.dao.SettingsDao

@Database(
    entities = [
        NotificationEventEntity::class,


            ],

    version = 1
)
abstract class CalendarInfoDB: RoomDatabase() {
    abstract val settingsDao: SettingsDao

    companion object{
        const val DATABASE_NAME = "CALENDAR_INFO"
    }
}
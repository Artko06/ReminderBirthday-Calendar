package com.example.reminderbirthday_calendar.di

import android.app.Application
import androidx.room.Room
import com.example.data.local.roomDb.dao.EventDao
import com.example.data.local.roomDb.dao.SettingsDao
import com.example.data.local.roomDb.database.CalendarInfoDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCalendarInfoDB(app: Application): CalendarInfoDB{
        return Room.databaseBuilder(
            context = app,
            klass = CalendarInfoDB::class.java,
            name = CalendarInfoDB.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideEventDao(database: CalendarInfoDB) : EventDao{
        return database.eventDao
    }

    @Provides
    fun provideSettingsDao(database: CalendarInfoDB): SettingsDao{
        return database.settingsDao
    }
}
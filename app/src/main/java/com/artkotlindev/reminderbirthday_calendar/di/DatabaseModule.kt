package com.artkotlindev.reminderbirthday_calendar.di

import android.app.Application
import androidx.room.Room
import com.artkotlindev.data.local.roomDb.dao.EventDao
import com.artkotlindev.data.local.roomDb.dao.SettingsDao
import com.artkotlindev.data.local.roomDb.database.CalendarInfoDB
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
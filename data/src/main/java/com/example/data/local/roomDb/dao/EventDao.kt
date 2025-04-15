package com.example.data.local.roomDb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.local.entity.settings.event.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("Select * FROM event")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Query("Select * FROM event WHERE eventType = :eventType")
    fun getByTypeEvents(eventType: String): Flow<List<EventEntity>>

    @Query("Select * FROM event WHERE id = :id")
    fun getByIdEvents(id: Int): Flow<EventEntity?>

    @Upsert
    suspend fun upsertEvent(event: EventEntity): Long

    @Upsert
    suspend fun upsertEvents(events: List<EventEntity>): List<Long>

    @Delete
    suspend fun deleteEvent(event: EventEntity): Int

    @Delete
    suspend fun deleteEvents(events: List<EventEntity>): Int
}
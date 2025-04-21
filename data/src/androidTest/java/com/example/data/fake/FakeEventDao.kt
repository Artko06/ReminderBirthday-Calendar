package com.example.data.fake

import com.example.data.local.entity.settings.event.EventEntity
import com.example.data.local.roomDb.dao.EventDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeEventDao(private val events: List<EventEntity>) : EventDao {
    override fun getAllEvents(): Flow<List<EventEntity>> = flowOf(events)

    override fun getByTypeEvents(eventType: String): Flow<List<EventEntity>> {
        TODO("Not yet implemented")
    }

    override fun getByIdEvents(id: Int): Flow<EventEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertEvent(event: EventEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun upsertEvents(events: List<EventEntity>): List<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEvent(event: EventEntity): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEvents(events: List<EventEntity>): Int {
        TODO("Not yet implemented")
    }
}
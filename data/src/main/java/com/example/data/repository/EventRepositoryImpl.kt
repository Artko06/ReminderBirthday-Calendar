package com.example.data.repository

import com.example.data.local.entity.settings.event.toData
import com.example.data.local.entity.settings.event.toDomain
import com.example.data.local.roomDb.dao.EventDao
import com.example.domain.models.event.Event
import com.example.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventDao: EventDao
): EventRepository {
    // Get
    override fun getAllEvents(): Flow<List<Event>> {
        return eventDao.getAllEvents().map { events ->
            events.map { event ->
                event.toDomain()
            }
        }
    }

    override fun getByTypeEvents(eventType: String): Flow<List<Event>> {
        return eventDao.getByTypeEvents(eventType = eventType).map { events ->
            events.map { event ->
                event.toDomain()
            }
        }
    }

    override fun getByIdEvent(id: Long): Flow<Event?> {
        return eventDao.getByIdEvents(id = id).map { event ->
            event?.toDomain()
        }
    }

    // Upsert
    override suspend fun upsertEvent(event: Event): Boolean {
        val success = eventDao.upsertEvent(event = event.toData())

        return success != 0L
    }

    override suspend fun upsertEvents(events: List<Event>): List<Boolean> {
        return eventDao.upsertEvents(events = events.map { event -> event.toData() }).map {
            it != 0L
        }
    }

    // Delete
    override suspend fun deleteEvent(event: Event): Boolean {
       val success = eventDao.deleteEvent(event = event.toData())

        return success != 0
    }

    override suspend fun deleteEvents(events: List<Event>): Int {
        return eventDao.deleteEvents(events = events.map { event -> event.toData() })
    }

    override suspend fun deleteAllEvents(): Int {
        eventDao.resetEventAutoIncrement()
        return eventDao.deleteAllEvents()
    }
}
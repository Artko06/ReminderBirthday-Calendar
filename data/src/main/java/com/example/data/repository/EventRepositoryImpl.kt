package com.example.data.repository

import com.example.data.local.entity.settings.event.toData
import com.example.data.local.entity.settings.event.toDomain
import com.example.data.local.roomDb.dao.EventDao
import com.example.domain.models.event.Event
import com.example.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepositoryImpl(
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

    override fun getByIdEvents(id: Int): Flow<Event?> {
        return eventDao.getByIdEvents(id = id).map { event ->
            event?.toDomain()
        }
    }

    // Upsert
    override suspend fun upsertEvent(event: Event) {
        eventDao.upsertEvent(event = event.toData())
    }

    override suspend fun upsertEvents(events: List<Event>) {
        eventDao.upsertEvents(events = events.map { event -> event.toData() })
    }

    // Delete
    override suspend fun deleteEvent(event: Event) {
        eventDao.deleteEvent(event = event.toData())
    }

    override suspend fun deleteEvents(events: List<Event>) {
        eventDao.deleteEvents(events = events.map { event -> event.toData() })
    }
}
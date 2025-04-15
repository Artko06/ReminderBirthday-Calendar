package com.example.domain.repository

import com.example.domain.models.event.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    // Get
    fun getAllEvents(): Flow<List<Event>>
    fun getByTypeEvents(eventType: String): Flow<List<Event>>
    fun getByIdEvents(id: Int): Flow<Event?>

    // Upsert
    suspend fun upsertEvent(event: Event): Boolean
    suspend fun upsertEvents(events: List<Event>): Boolean

    // Delete
    suspend fun deleteEvent(event: Event): Boolean
    suspend fun deleteEvents(events: List<Event>): Boolean
}
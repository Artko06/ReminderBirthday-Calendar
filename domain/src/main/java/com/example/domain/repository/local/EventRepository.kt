package com.example.domain.repository.local

import com.example.domain.models.event.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    // Get
    fun getAllEvents(): Flow<List<Event>>
    fun getByTypeEvents(eventType: String): Flow<List<Event>>
    fun getByIdEvent(id: Long): Flow<Event?>

    // Upsert
    suspend fun upsertEvent(event: Event): Boolean
    suspend fun upsertEvents(events: List<Event>): List<Boolean>

    // Delete
    suspend fun deleteEvent(event: Event): Boolean
    suspend fun deleteEvents(events: List<Event>): Int
    suspend fun deleteAllEvents(): Int
}
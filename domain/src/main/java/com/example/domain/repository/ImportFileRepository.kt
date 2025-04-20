package com.example.domain.repository

import com.example.domain.models.event.Event

interface ImportFileRepository {
    suspend fun importEventsFromCsv(): List<Event>
    suspend fun importEventsFromJson(): List<Event>
}
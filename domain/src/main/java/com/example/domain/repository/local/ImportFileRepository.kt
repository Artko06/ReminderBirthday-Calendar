package com.example.domain.repository.local

import com.example.domain.models.event.Event

interface ImportFileRepository {
    suspend fun importEventsFromCsv(strUri: String): List<Event>
    suspend fun importEventsFromJson(strUri: String): List<Event>
}
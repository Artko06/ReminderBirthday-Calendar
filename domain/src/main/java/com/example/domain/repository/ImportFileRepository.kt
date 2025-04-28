package com.example.domain.repository

import com.example.domain.models.event.Event

interface ImportFileRepository {
    suspend fun importEventsFromCsv(strUri: String): List<Event>
    suspend fun importEventsFromJson(strUri: String): List<Event>
}
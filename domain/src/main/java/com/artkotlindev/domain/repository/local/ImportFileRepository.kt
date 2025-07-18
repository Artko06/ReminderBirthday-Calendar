package com.artkotlindev.domain.repository.local

import com.artkotlindev.domain.models.event.Event

interface ImportFileRepository {
    suspend fun importEventsFromCsv(strUri: String): List<Event>
    suspend fun importEventsFromJson(strUri: String): List<Event>
}
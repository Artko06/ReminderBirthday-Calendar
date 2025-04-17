package com.example.domain.repository

import com.example.domain.models.event.Event
import java.io.File

interface ImportFileRepository {
    suspend fun importEventsFromCsv(file: File): List<Event>
    suspend fun importEventsFromJson(file: File): List<Event>
}
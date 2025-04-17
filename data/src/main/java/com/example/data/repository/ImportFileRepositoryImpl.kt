package com.example.data.repository

import com.example.domain.models.event.Event
import com.example.domain.repository.ImportFileRepository
import java.io.File

class ImportFileRepositoryImpl: ImportFileRepository {
    override suspend fun importEventsFromCsv(file: File): List<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun importEventsFromJson(file: File): List<Event> {
        TODO("Not yet implemented")
    }
}
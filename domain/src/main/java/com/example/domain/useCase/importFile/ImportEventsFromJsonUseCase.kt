package com.example.domain.useCase.importFile

import com.example.domain.models.event.Event
import com.example.domain.repository.local.ImportFileRepository

class ImportEventsFromJsonUseCase(
    private val repository: ImportFileRepository
) {
    suspend operator fun invoke(strUri: String): List<Event> = repository.importEventsFromJson(strUri)
}
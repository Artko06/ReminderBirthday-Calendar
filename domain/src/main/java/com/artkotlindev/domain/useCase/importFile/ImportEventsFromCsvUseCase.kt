package com.artkotlindev.domain.useCase.importFile

import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.repository.local.ImportFileRepository

class ImportEventsFromCsvUseCase(
    private val repository: ImportFileRepository
) {
    suspend operator fun invoke(strUri: String): List<Event> = repository.importEventsFromCsv(strUri)
}
package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.repository.ContactImportRepository

class ImportEventUseCase(private val repository: ContactImportRepository) {
    suspend operator fun invoke(): List<Event> = repository.importEvents()
}
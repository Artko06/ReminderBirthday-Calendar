package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.repository.ContactAppRepository

class ImportEventFromContactsUseCase(private val repository: ContactAppRepository) {
    suspend operator fun invoke(): List<Event> = repository.importEvents()
}
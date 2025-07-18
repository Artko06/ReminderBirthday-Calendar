package com.artkotlindev.domain.useCase.calendar.event

import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.repository.local.ContactAppRepository

class ImportEventsFromContactsUseCase(
    private val repository: ContactAppRepository
) {
    suspend operator fun invoke(): List<Event> = repository.importEvents()
}

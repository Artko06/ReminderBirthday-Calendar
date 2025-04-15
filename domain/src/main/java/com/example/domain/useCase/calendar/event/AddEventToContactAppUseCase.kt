package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.EventType
import com.example.domain.repository.ContactAppRepository
import java.time.LocalDate

class AddEventToContactAppUseCase(private val repository: ContactAppRepository) {
    suspend operator fun invoke(
        contactId: String,
        eventDate: LocalDate,
        eventType: EventType
    ): Boolean = repository.addEvent(
        contactId = contactId,
        eventDate = eventDate.toString(),
        eventType = eventType
    )
}
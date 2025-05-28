package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.EventType
import com.example.domain.repository.local.ContactAppRepository
import java.time.LocalDate

class AddEventToContactAppUseCase(private val repository: ContactAppRepository) {
    suspend operator fun invoke(
        contactId: String,
        eventDate: LocalDate,
        eventType: EventType,
        yearMatter: Boolean
    ): Boolean = repository.addEvent(
        contactId = contactId,
        eventDate = if (yearMatter) eventDate.toString() // "yyyy-MM-dd"
            else "-" + eventDate.toString().substring(4), // "--MM-dd"
        eventType = eventType
    )
}
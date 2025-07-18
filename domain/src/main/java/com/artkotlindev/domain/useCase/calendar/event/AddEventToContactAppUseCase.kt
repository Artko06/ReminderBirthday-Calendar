package com.artkotlindev.domain.useCase.calendar.event

import com.artkotlindev.domain.models.event.EventType
import com.artkotlindev.domain.repository.local.ContactAppRepository
import java.time.LocalDate

class AddEventToContactAppUseCase(private val repository: ContactAppRepository) {
    suspend operator fun invoke(
        contactId: String,
        eventDate: LocalDate,
        eventType: EventType,
        yearMatter: Boolean,
        customLabel: String?
    ): Boolean = repository.addEvent(
        contactId = contactId,
        eventDate = if (yearMatter) eventDate.toString() // "yyyy-MM-dd"
            else "-" + eventDate.toString().substring(4), // "--MM-dd"
        eventType = eventType,
        customLabel = customLabel
    )
}
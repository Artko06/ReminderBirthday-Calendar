package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.repository.EventRepository
import java.time.LocalDate

class UpsertEventUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(
        eventType: EventType,
        nameContact: String,
        surnameContact: String?,
        originalDate: LocalDate,
        yearMatter: Boolean,
        notes: String?,
        image: ByteArray?
    ): Boolean {
        return repository.upsertEvent(
            event = Event(
                id = 0,
                eventType = eventType,
                nameContact = nameContact,
                surnameContact = surnameContact,
                originalDate = originalDate,
                yearMatter = yearMatter,
                notes = notes,
                image = image
            )
        )
    }

    suspend operator fun invoke(event: Event): Boolean = repository.upsertEvent(event = event)
}
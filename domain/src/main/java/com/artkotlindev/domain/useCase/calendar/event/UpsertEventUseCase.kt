package com.artkotlindev.domain.useCase.calendar.event

import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.models.event.EventType
import com.artkotlindev.domain.models.event.SortTypeEvent
import com.artkotlindev.domain.repository.local.EventRepository
import java.time.LocalDate

class UpsertEventUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(
        idContact: String?,
        eventType: EventType,
        sortTypeEvent: SortTypeEvent,
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
                idContact = idContact,
                eventType = eventType,
                sortTypeEvent = sortTypeEvent,
                nameContact = nameContact,
                surnameContact = surnameContact,
                originalDate = originalDate,
                yearMatter = yearMatter,
                notes = notes,
                image = image
            )
        )
    }

    suspend operator fun invoke(
        id: Long,
        idContact: String?,
        eventType: EventType,
        sortTypeEvent: SortTypeEvent,
        nameContact: String,
        surnameContact: String?,
        originalDate: LocalDate,
        yearMatter: Boolean,
        notes: String?,
        image: ByteArray?
    ): Boolean {
        return repository.upsertEvent(
            event = Event(
                id = id,
                idContact = idContact,
                eventType = eventType,
                sortTypeEvent = sortTypeEvent,
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
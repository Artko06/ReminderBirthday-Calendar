package com.artkotlindev.domain.useCase.calendar.event

import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.repository.local.EventRepository

class UpsertEventsUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(events: List<Event>): List<Boolean> {
        return repository.upsertEvents(events = events)
    }
}
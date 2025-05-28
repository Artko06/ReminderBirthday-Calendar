package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.repository.local.EventRepository

class UpsertEventsUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(events: List<Event>): List<Boolean> {
        return repository.upsertEvents(events = events)
    }
}
package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.repository.local.EventRepository

class DeleteEventsUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(events: List<Event>): Int {
        return repository.deleteEvents(events = events)
    }
}
package com.artkotlindev.domain.useCase.calendar.event

import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.repository.local.EventRepository

class DeleteEventsUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(events: List<Event>): Int {
        return repository.deleteEvents(events = events)
    }
}
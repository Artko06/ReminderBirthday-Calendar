package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.repository.local.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventByTypeUseCase(private val repository: EventRepository) {
    operator fun invoke(eventType: EventType): Flow<List<Event>> =
        repository.getByTypeEvents(eventType = eventType.name)
}
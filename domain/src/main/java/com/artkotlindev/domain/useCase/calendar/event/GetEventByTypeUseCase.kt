package com.artkotlindev.domain.useCase.calendar.event

import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.models.event.EventType
import com.artkotlindev.domain.repository.local.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventByTypeUseCase(private val repository: EventRepository) {
    operator fun invoke(eventType: EventType): Flow<List<Event>> =
        repository.getByTypeEvents(eventType = eventType.name)
}
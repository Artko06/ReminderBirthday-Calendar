package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.repository.local.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventByIdUseCase(
    private val repository: EventRepository
) {
    operator fun invoke(idEvent: Long): Flow<Event?>{
        return repository.getByIdEvent(id = idEvent)
    }
}
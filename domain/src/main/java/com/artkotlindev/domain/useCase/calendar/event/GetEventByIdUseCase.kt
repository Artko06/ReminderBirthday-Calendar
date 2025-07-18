package com.artkotlindev.domain.useCase.calendar.event

import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.repository.local.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventByIdUseCase(
    private val repository: EventRepository
) {
    operator fun invoke(idEvent: Long): Flow<Event?>{
        return repository.getByIdEvent(id = idEvent)
    }
}
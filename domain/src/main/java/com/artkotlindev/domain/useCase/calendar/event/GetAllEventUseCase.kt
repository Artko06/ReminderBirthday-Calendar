package com.artkotlindev.domain.useCase.calendar.event

import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.repository.local.EventRepository
import kotlinx.coroutines.flow.Flow

class GetAllEventUseCase(private val repository: EventRepository) {
    operator fun invoke(): Flow<List<Event>> = repository.getAllEvents()
}
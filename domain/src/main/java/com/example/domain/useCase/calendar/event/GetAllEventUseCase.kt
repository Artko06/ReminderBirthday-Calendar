package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetAllEventUseCase(private val repository: EventRepository) {
    operator fun invoke(): Flow<List<Event>> = repository.getAllEvents()
}
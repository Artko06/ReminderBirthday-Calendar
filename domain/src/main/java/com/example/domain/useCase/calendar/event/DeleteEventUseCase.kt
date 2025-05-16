package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.repository.EventRepository
import kotlinx.coroutines.flow.firstOrNull

class DeleteEventUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(idEvent: Long): Boolean {
        val event = repository.getByIdEvent(idEvent).firstOrNull()

        return if (event != null) {
            return repository.deleteEvent(event = event)
        } else false
    }

    suspend operator fun invoke(event: Event): Boolean = repository.deleteEvent(event = event)
}
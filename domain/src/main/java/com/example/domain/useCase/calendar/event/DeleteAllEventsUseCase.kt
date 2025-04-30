package com.example.domain.useCase.calendar.event

import com.example.domain.repository.EventRepository

class DeleteAllEventsUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(): Int = repository.deleteAllEvents()
}
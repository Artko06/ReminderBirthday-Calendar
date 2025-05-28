package com.example.domain.useCase.calendar.event

import com.example.domain.repository.local.EventRepository

class DeleteAllEventsUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(): Int = repository.deleteAllEvents()
}
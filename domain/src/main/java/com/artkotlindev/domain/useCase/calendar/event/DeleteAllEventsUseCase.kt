package com.artkotlindev.domain.useCase.calendar.event

import com.artkotlindev.domain.repository.local.EventRepository

class DeleteAllEventsUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(): Int = repository.deleteAllEvents()
}
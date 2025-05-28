package com.example.domain.useCase.google

import com.example.domain.models.event.Event
import com.example.domain.repository.remote.GoogleClientRepository

class GetEventsFromRemoteUseCase(
    private val repository: GoogleClientRepository
) {
    suspend operator fun invoke(): List<Event> = repository.getEventsFromRemote()
}
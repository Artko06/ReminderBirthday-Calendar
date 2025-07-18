package com.artkotlindev.domain.useCase.google

import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.repository.remote.GoogleClientRepository

class GetEventsFromRemoteUseCase(
    private val repository: GoogleClientRepository
) {
    suspend operator fun invoke(): List<Event> = repository.getEventsFromRemote()
}
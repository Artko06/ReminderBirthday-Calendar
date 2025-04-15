package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventByContactNameUseCase(private val repository: EventRepository) {
    operator fun invoke(strSearch: String) : Flow<List<Event>> {
        return if (strSearch.isNotEmpty()){
            repository.getAllEvents()
        } else repository.getAllEvents()
    }
}
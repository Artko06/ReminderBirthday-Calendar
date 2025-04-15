package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetEventByContactNameUseCase(private val repository: EventRepository) {
    operator fun invoke(strSearch: String) : Flow<List<Event>> {
        return repository.getAllEvents().map { events ->
            if (strSearch.isBlank()) events
            else {
                val query = strSearch.trim().lowercase()
                events.filter { event ->
                    var fullNameContact: String = event.nameContact.lowercase()
                    if (event.surnameContact != null){
                        fullNameContact += " " + event.surnameContact.lowercase()
                    }

                    query in fullNameContact
                }
            }
        }
    }
}
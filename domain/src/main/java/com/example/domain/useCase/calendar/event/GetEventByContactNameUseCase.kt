package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.models.event.SortTypeEvent
import com.example.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetEventByContactNameUseCase(private val repository: EventRepository) {
    operator fun invoke(strSearch: String, sortTypeEvent: SortTypeEvent? = null) : Flow<List<Event>> {
        return repository.getAllEvents().map { events ->
            if (strSearch.isBlank()) {
                if (sortTypeEvent != null) events.filter { it.sortTypeEvent == sortTypeEvent }
                else events
            }
            else {
                val query = strSearch.trim().lowercase()
                val srtSearchEvent = events.filter { event ->
                    var fullNameContact: String = event.nameContact.lowercase()
                    if (event.surnameContact != null){
                        fullNameContact += " " + event.surnameContact.lowercase()
                    }

                    query in fullNameContact
                }

                if (sortTypeEvent != null) srtSearchEvent.filter { it.sortTypeEvent == sortTypeEvent }
                else srtSearchEvent
            }
        }
    }
}
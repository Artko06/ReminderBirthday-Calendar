package com.artkotlindev.domain.useCase.calendar.event

import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.models.event.SortTypeEvent
import com.artkotlindev.domain.repository.local.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetEventsBySortTypeUseCase(
    private val repository: EventRepository
) {
    operator fun invoke(sortTypeEvent: SortTypeEvent?): Flow<List<Event>> {
        return repository.getAllEvents().map { events ->
            sortTypeEvent?.let { sortType ->
                events.filter { it.sortTypeEvent == sortType }
            } ?: events
        }
    }
}
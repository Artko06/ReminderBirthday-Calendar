package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event

class GetEventsByMonthUseCase {
    operator fun invoke(events: List<Event>, month: Int): List<Event> {
        return events.filter { it.originalDate.month.value == month }
    }
}
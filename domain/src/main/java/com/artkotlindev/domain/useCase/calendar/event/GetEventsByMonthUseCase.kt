package com.artkotlindev.domain.useCase.calendar.event

import com.artkotlindev.domain.models.event.Event

class GetEventsByMonthUseCase {
    operator fun invoke(events: List<Event>, month: Int): List<Event> {
        return events.filter { it.originalDate.month.value == month }
    }
}
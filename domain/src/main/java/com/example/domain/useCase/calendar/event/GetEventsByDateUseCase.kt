package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import java.time.LocalDate

class GetEventsByDateUseCase {
    operator fun invoke(events: List<Event>, date: LocalDate): List<Event> {
        return events.filter { it.originalDate.withYear(2000) == date.withYear(2000)  } // 2000 - any year
    }
}
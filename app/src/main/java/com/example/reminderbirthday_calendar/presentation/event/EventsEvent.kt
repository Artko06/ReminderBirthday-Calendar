package com.example.reminderbirthday_calendar.presentation.event

import com.example.domain.models.event.Event
import com.example.domain.models.event.SortTypeEvent

sealed class EventsEvent {
    object ImportEventsFromContacts: EventsEvent()
    data class UpdateSearchLine(val newValue: String): EventsEvent()
    data class UpdateEvents(val events: List<Event>): EventsEvent()
    object ClearEvents: EventsEvent()

    object ShowDeleteAllEventsDialog: EventsEvent()
    object CloseDeleteAllEventsDialog: EventsEvent()

    object ShowReadContactPermDialog: EventsEvent()
    object CloseReadContactPermDialog: EventsEvent()

    object ChangeStatusViewDaysLeft: EventsEvent()

    data class SelectSortType(val sortType: SortTypeEvent?): EventsEvent()
}
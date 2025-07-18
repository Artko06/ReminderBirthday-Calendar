package com.artkotlindev.reminderbirthday_calendar.presentation.event

import com.artkotlindev.domain.models.event.SortTypeEvent

sealed class EventsEvent {
    object ImportEventsFromContacts: EventsEvent()
    data class UpdateSearchLine(val newValue: String): EventsEvent()
    object ClearEvents: EventsEvent()

    object ShowDeleteAllEventsDialog: EventsEvent()
    object CloseDeleteAllEventsDialog: EventsEvent()

    object ShowReadContactPermDialog: EventsEvent()
    object CloseReadContactPermDialog: EventsEvent()

    object ChangeStatusViewDaysLeft: EventsEvent()

    data class SelectSortType(val sortType: SortTypeEvent?): EventsEvent()
}
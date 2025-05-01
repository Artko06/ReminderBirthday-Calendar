package com.example.reminderbirthday_calendar.presentation.state

import com.example.domain.models.event.Event

data class EventsState(
    val events: List<Event> = emptyList<Event>(),
    val filterEvents: List<Event> = emptyList<Event>(),
    val searchStr: String = "",
    val isViewDaysLeft: Boolean = false,

    val isShowAllEventsDeleteDialog: Boolean = false,
    val isShowReadContactPermDialog: Boolean = false
)
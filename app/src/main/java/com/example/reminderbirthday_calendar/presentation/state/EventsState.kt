package com.example.reminderbirthday_calendar.presentation.state

import com.example.domain.models.event.Event
import com.example.domain.models.event.SortTypeEvent

data class EventsState(
    val events: List<Event> = emptyList<Event>(),
    val allEventsSize: Int = 0,
    val sortTypeEvent: SortTypeEvent? = null,
    val filterEvents: List<Event> = emptyList<Event>(),
    val searchStr: String = "",
    val isViewDaysLeft: Boolean = false,

    val isShowAllEventsDeleteDialog: Boolean = false,
    val isShowReadContactPermDialog: Boolean = false,

    val isLoadingImportEvents: Boolean = false
)
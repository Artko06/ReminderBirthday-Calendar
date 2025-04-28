package com.example.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.calendar.event.DeleteEventsUseCase
import com.example.domain.useCase.calendar.event.GetAllEventUseCase
import com.example.domain.useCase.calendar.event.GetEventByContactNameUseCase
import com.example.domain.useCase.calendar.event.ImportEventFromContactsUseCase
import com.example.domain.useCase.calendar.event.UpsertEventsUseCase
import com.example.domain.util.extensionFunc.sortByClosestDate
import com.example.reminderbirthday_calendar.presentation.event.EventsEvent
import com.example.reminderbirthday_calendar.presentation.state.EventsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class EventsViewModel @Inject constructor(
    private val importEventFromContactsUseCase: ImportEventFromContactsUseCase,
    private val getEventByContactNameUseCase: GetEventByContactNameUseCase,
    private val getAllEventUseCase: GetAllEventUseCase,
    private val upsertEventUseCase: UpsertEventsUseCase,
    private val deleteEventsUseCase: DeleteEventsUseCase,
): ViewModel() {
    private val _eventsState = MutableStateFlow(EventsState())
    private val _searchLine = MutableStateFlow("")
    private val _filterEvents = _searchLine.flatMapLatest { searchLine ->
        getEventByContactNameUseCase(strSearch = searchLine)
    }
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = emptyList())

    val eventState = combine(_eventsState, _searchLine, _filterEvents) {
        eventState, searchLine, filterEvents ->

        eventState.copy(
            filterEvents = filterEvents.sortByClosestDate(),
            searchStr = searchLine
        )
    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = EventsState())

    init {
        viewModelScope.launch {
            _eventsState.update { it.copy(
                events = getAllEventUseCase.invoke().first().sortByClosestDate() // From database
            ) }
        }
    }

    fun onEvent(event: EventsEvent){
        when(event){
            EventsEvent.ImportEventsFromContacts -> {
                viewModelScope.launch {
                    val currentEvents = _eventsState.value.events.toMutableSet()

                    currentEvents.addAll(importEventFromContactsUseCase())

                    _eventsState.update { it.copy(
                        events = currentEvents.toList().sortByClosestDate()
                    )
                    }

                    upsertEventUseCase(events = currentEvents.toList())
                }
            }

            is EventsEvent.UpdateSearchLine -> {
                _searchLine.value = event.newValue
            }

            is EventsEvent.UpdateEvents -> {
                val gotEvents = event.events

                val gotEventsId0 = gotEvents.map { event ->
                    event.copy(id = 0)
                }

                viewModelScope.launch {
                    upsertEventUseCase(events = gotEventsId0)

                    val eventsFromDb = getAllEventUseCase.invoke().first()

                    _eventsState.update {
                        it.copy(
                            events = eventsFromDb.sortByClosestDate()
                        )
                    }
                }
            }

            EventsEvent.ClearEvents -> {
                _eventsState.update { it.copy(
                    events = emptyList()
                ) }

                viewModelScope.launch {
                    deleteEventsUseCase.invoke(events = getAllEventUseCase.invoke().first())
                }
            }


        }
    }
}
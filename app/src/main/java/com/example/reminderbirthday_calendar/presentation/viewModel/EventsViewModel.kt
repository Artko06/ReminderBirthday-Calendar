package com.example.reminderbirthday_calendar.presentation.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.calendar.event.DeleteAllEventsUseCase
import com.example.domain.useCase.calendar.event.GetAllEventUseCase
import com.example.domain.useCase.calendar.event.GetEventByContactNameUseCase
import com.example.domain.useCase.calendar.event.ImportEventsFromContactsUseCase
import com.example.domain.useCase.calendar.event.UpsertEventsUseCase
import com.example.domain.util.extensionFunc.sortByClosestDate
import com.example.reminderbirthday_calendar.presentation.event.EventsEvent
import com.example.reminderbirthday_calendar.presentation.sharedFlow.EventsSharedFlow
import com.example.reminderbirthday_calendar.presentation.sharedFlow.EventsSharedFlow.ShowToast
import com.example.reminderbirthday_calendar.presentation.state.EventsState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
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
    private val importEventsFromContactsUseCase: ImportEventsFromContactsUseCase,
    private val getEventByContactNameUseCase: GetEventByContactNameUseCase,
    private val getAllEventUseCase: GetAllEventUseCase,
    private val upsertEventUseCase: UpsertEventsUseCase,
    private val deleteAllEventsUseCase: DeleteAllEventsUseCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private val _eventsState = MutableStateFlow(EventsState())
    private val _searchLine = MutableStateFlow("")
    private val _filterEvents = _searchLine.flatMapLatest { searchLine ->
        getEventByContactNameUseCase(strSearch = searchLine)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val eventState =
        combine(_eventsState, _searchLine, _filterEvents) { eventState, searchLine, filterEvents ->

            eventState.copy(
                filterEvents = filterEvents.sortByClosestDate(),
                searchStr = searchLine
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = EventsState()
        )

    private val _eventsSharedFlow = MutableSharedFlow<EventsSharedFlow>()
    val eventsSharedFlow = _eventsSharedFlow.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _eventsState.update {
                it.copy(
                    events = getAllEventUseCase.invoke().first()
                        .sortByClosestDate() // From database
                )
            }
        }
    }

    fun onEvent(event: EventsEvent) {
        when (event) {
            EventsEvent.ImportEventsFromContacts -> {
                val permissionReadState =
                    ContextCompat.checkSelfPermission(appContext, Manifest.permission.READ_CONTACTS) ==
                            PackageManager.PERMISSION_GRANTED

                if (!permissionReadState){
                    _eventsState.update { it.copy(
                        isShowReadContactPermDialog = true
                    ) }
                    return
                }

                viewModelScope.launch(Dispatchers.IO) {
                    val importEvents = importEventsFromContactsUseCase.invoke()

                    val importEventsId0 = importEvents.map { event ->
                        event.copy(id = 0)
                    }

                    val eventsDbBeforeAdding = getAllEventUseCase.invoke().first()

                    upsertEventUseCase(events = importEventsId0)

                    val eventsDbAfterAdding = getAllEventUseCase.invoke().first()

                    _eventsState.update {
                        it.copy(
                            events = eventsDbAfterAdding.sortByClosestDate()
                        )
                    }

                    _eventsSharedFlow.emit(
                        value = ShowToast(
                            message = "${importEvents.size} imported events. " +
                                    "${eventsDbAfterAdding.size - eventsDbBeforeAdding.size} added events")
                    )
                }
            }

            is EventsEvent.UpdateSearchLine -> {
                _searchLine.value = event.newValue
            }

            is EventsEvent.UpdateEvents -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val importEvents = event.events

                    val importEventsId0 = importEvents.map { event ->
                        event.copy(id = 0)
                    }

                    val eventsDbBeforeAdding = getAllEventUseCase.invoke().first()

                    upsertEventUseCase(events = importEventsId0)

                    val eventsDbAfterAdding = getAllEventUseCase.invoke().first()

                    _eventsState.update {
                        it.copy(
                            events = eventsDbAfterAdding.sortByClosestDate()
                        )
                    }

                    _eventsSharedFlow.emit(
                        value = ShowToast(
                            message = "${importEvents.size} imported events. " +
                                    "${eventsDbAfterAdding.size - eventsDbBeforeAdding.size} added events")
                    )
                }
            }

            EventsEvent.ClearEvents -> {
                _eventsState.update {
                    it.copy(
                        events = emptyList()
                    )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    val deleted = deleteAllEventsUseCase()

                    _eventsSharedFlow.emit(
                        value = ShowToast("$deleted events deleted")
                    )
                }
            }

            EventsEvent.ShowDeleteAllEventsDialog -> {
                _eventsState.update { it.copy(
                    isShowAllEventsDeleteDialog = true
                ) }
            }

            EventsEvent.CloseDeleteAllEventsDialog -> {
                _eventsState.update { it.copy(
                    isShowAllEventsDeleteDialog = false
                ) }
            }

            EventsEvent.ShowReadContactPermDialog -> {
                _eventsState.update { it.copy(
                    isShowReadContactPermDialog = true
                ) }
            }

            EventsEvent.CloseReadContactPermDialog -> {
                _eventsState.update { it.copy(
                    isShowReadContactPermDialog = false
                ) }
            }


        }
    }
}
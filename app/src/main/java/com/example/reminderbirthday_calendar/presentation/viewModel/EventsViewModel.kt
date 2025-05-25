package com.example.reminderbirthday_calendar.presentation.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.useCase.calendar.event.DeleteAllEventsUseCase
import com.example.domain.useCase.calendar.event.GetAllEventUseCase
import com.example.domain.useCase.calendar.event.GetEventByContactNameUseCase
import com.example.domain.useCase.calendar.event.ImportEventsFromContactsUseCase
import com.example.domain.useCase.calendar.event.UpsertEventsUseCase
import com.example.domain.useCase.settings.notification.CancelNotifyAllEventUseCase
import com.example.domain.useCase.settings.notification.ScheduleAllEventsUseCase
import com.example.domain.useCase.settings.showTypeEvent.GetStatusShowAnniversaryEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.GetStatusShowBirthdayEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.GetStatusShowOtherEventUseCase
import com.example.domain.useCase.settings.viewDaysLeft.GetStatusViewDaysLeftUseCase
import com.example.domain.useCase.settings.viewDaysLeft.SetStatusViewDaysLeftUseCase
import com.example.domain.util.extensionFunc.sortByClosestDate
import com.example.reminderbirthday_calendar.R
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
    private val getStatusViewDaysLeftUseCase: GetStatusViewDaysLeftUseCase,
    private val setStatusViewDaysLeftUseCase: SetStatusViewDaysLeftUseCase,

    private val getStatusShowAnniversaryEventUseCase: GetStatusShowAnniversaryEventUseCase,
    private val getStatusShowBirthdayEventUseCase: GetStatusShowBirthdayEventUseCase,
    private val getStatusShowOtherEventUseCase: GetStatusShowOtherEventUseCase,

    private val scheduleAllEventsUseCase: ScheduleAllEventsUseCase,
    private val cancelNotifyAllEventUseCase: CancelNotifyAllEventUseCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private val _eventsState = MutableStateFlow(EventsState())
    private val _searchLine = MutableStateFlow("")
    private val _allEventsDatabase = getAllEventUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val _filterEvents = _searchLine.flatMapLatest { searchLine ->
        getEventByContactNameUseCase(strSearch = searchLine)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val _birthdayVisible = getStatusShowBirthdayEventUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = true
        )

    private val _anniversaryVisible = getStatusShowAnniversaryEventUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = true
        )

    private val _otherVisible = getStatusShowOtherEventUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = true
        )

    val eventState = combine(
        listOf(
            _eventsState,
            _allEventsDatabase,
            _searchLine,
            _filterEvents,
            _birthdayVisible,
            _anniversaryVisible,
            _otherVisible
        )
    ) { values ->

        val eventState = values[0] as EventsState
        val allEvents = values[1] as List<Event>
        val searchLine = values[2] as String
        val filterEvents = values[3] as List<Event>
        val birthdayVisible = values[4] as Boolean
        val anniversaryVisible = values[5] as Boolean
        val otherVisible = values[6] as Boolean

        val typeVisibilityMap = mapOf(
            EventType.BIRTHDAY to birthdayVisible,
            EventType.ANNIVERSARY to anniversaryVisible,
            EventType.OTHER to otherVisible
        )

        val visibleAllEvents = allEvents.filter { typeVisibilityMap[it.eventType] == true }
        val visibleFilterEvents = filterEvents.filter { typeVisibilityMap[it.eventType] == true }

        val sortAllEvents = if (eventState.sortTypeEvent != null) {
            visibleAllEvents.filter { it.sortTypeEvent == eventState.sortTypeEvent }
        } else visibleAllEvents
        val sortFilterEvents = if (eventState.sortTypeEvent != null){
            visibleFilterEvents.filter { it.sortTypeEvent == eventState.sortTypeEvent }
        } else visibleFilterEvents

        eventState.copy(
            events = sortAllEvents.sortByClosestDate(),
            filterEvents = sortFilterEvents.sortByClosestDate(),
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
                        .sortByClosestDate(), // From database
                    isViewDaysLeft = getStatusViewDaysLeftUseCase.invoke().first() // From DataStore
                )
            }
        }
    }

    fun onEvent(event: EventsEvent) {
        when (event) {
            EventsEvent.ImportEventsFromContacts -> {
                _eventsState.update { it.copy(
                    isLoadingImportEvents = true
                ) }

                val permissionReadState =
                    ContextCompat.checkSelfPermission(appContext, Manifest.permission.READ_CONTACTS) ==
                            PackageManager.PERMISSION_GRANTED

                if (!permissionReadState){
                    _eventsState.update { it.copy(
                        isShowReadContactPermDialog = true,
                        isLoadingImportEvents = false
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

                    scheduleAllEventsUseCase()

                    _eventsSharedFlow.emit(
                        value = ShowToast(
                            messageResId = R.string.events_import_result,
                            formatArgs = listOf(
                                importEvents.size.toString(),
                                (eventsDbAfterAdding.size - eventsDbBeforeAdding.size).toString()
                            )
                        ))

                    _eventsState.update { it.copy(
                        isLoadingImportEvents = false
                    ) }
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

                    scheduleAllEventsUseCase()

                    _eventsState.update {
                        it.copy(
                            events = eventsDbAfterAdding.sortByClosestDate()
                        )
                    }

                    _eventsSharedFlow.emit(
                        value = ShowToast(
                            messageResId = R.string.events_import_result,
                            formatArgs = listOf(
                                importEvents.size.toString(),
                                (eventsDbAfterAdding.size - eventsDbBeforeAdding.size).toString()
                            )
                    ))
                }
            }

            EventsEvent.ClearEvents -> {
                _eventsState.update {
                    it.copy(
                        events = emptyList()
                    )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    cancelNotifyAllEventUseCase()
                    val deleted = deleteAllEventsUseCase.invoke()

                    _eventsSharedFlow.emit(
                        value = ShowToast(
                            messageResId = R.string.events_deleted,
                            formatArgs = listOf(deleted.toString())
                        )
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

            EventsEvent.ChangeStatusViewDaysLeft -> {
                _eventsState.update { it.copy(
                    isViewDaysLeft = !it.isViewDaysLeft
                ) }

                viewModelScope.launch(Dispatchers.IO) {
                    setStatusViewDaysLeftUseCase(activeStatus = _eventsState.value.isViewDaysLeft)
                }
            }

            is EventsEvent.SelectSortType -> {
                _eventsState.update { it.copy(
                    sortTypeEvent = event.sortType
                ) }
            }


        }
    }
}
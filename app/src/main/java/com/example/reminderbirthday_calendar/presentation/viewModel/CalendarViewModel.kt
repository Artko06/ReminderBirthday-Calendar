package com.example.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.calendar.event.GetAllEventUseCase
import com.example.domain.useCase.calendar.event.GetEventsByDateUseCase
import com.example.domain.useCase.calendar.event.GetEventsByMonthUseCase
import com.example.domain.util.extensionFunc.sortByClosestDate
import com.example.reminderbirthday_calendar.presentation.event.CalendarEvent
import com.example.reminderbirthday_calendar.presentation.state.CalendarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getAllEventUseCase: GetAllEventUseCase,
    private val getEventsByDateUseCase: GetEventsByDateUseCase,
    private val getEventsByMonthUseCase: GetEventsByMonthUseCase
): ViewModel() {
    private val _calendarState = MutableStateFlow(CalendarState())

    private val _allEventsDatabase = getAllEventUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val calendarState = combine(
        _allEventsDatabase,
        _calendarState
    ) { allEventsDatabase, calendarState ->
        _calendarState.value.copy(
            events = allEventsDatabase,
            eventsInDate = if (calendarState.selectDate != null){
                getEventsByDateUseCase
                    .invoke(events = allEventsDatabase, date = calendarState.selectDate)
                    .sortByClosestDate()
            } else getEventsByMonthUseCase(
                events = allEventsDatabase,
                month = calendarState.currentMonth.plusMonths(calendarState.calendarPage.toLong()).month.value
            ).sortByClosestDate()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CalendarState()
    )

    fun onEvent(event: CalendarEvent){
        when(event){
            is CalendarEvent.SelectDate -> {
                _calendarState.update { it.copy(
                    selectDate = event.date
                ) }
            }

            is CalendarEvent.ChangeCalendarPage -> {
                _calendarState.update { it.copy(
                    calendarPage = event.newPage
                ) }
            }


        }
    }
}
package com.example.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.calendar.event.GetEventByIdUseCase
import com.example.reminderbirthday_calendar.presentation.navigation.model.EVENT_ID_KEY
import com.example.reminderbirthday_calendar.presentation.state.EventDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getEventByIdUseCase: GetEventByIdUseCase
): ViewModel() {

    private val _eventDetailState = MutableStateFlow(EventDetailState())
    val eventDetailState = _eventDetailState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val eventId = savedStateHandle.get<Long>(EVENT_ID_KEY)

            val gotEvent = (if (eventId != null) getEventByIdUseCase(eventId) else null)?.first()

            _eventDetailState.update { it.copy(
                event = gotEvent
            ) }
        }
    }
}
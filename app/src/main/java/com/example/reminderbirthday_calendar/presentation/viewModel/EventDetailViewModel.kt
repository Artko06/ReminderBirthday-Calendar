package com.example.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.event.Event
import com.example.domain.useCase.calendar.event.DeleteEventUseCase
import com.example.domain.useCase.calendar.event.GetEventByIdUseCase
import com.example.domain.useCase.calendar.zodiac.GetChineseZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.GetWesternZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.status.GetStatusChineseZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.status.GetStatusWesternZodiacUseCase
import com.example.reminderbirthday_calendar.presentation.event.DetailInfoEvent
import com.example.reminderbirthday_calendar.presentation.navigation.model.EVENT_ID_KEY
import com.example.reminderbirthday_calendar.presentation.state.EventDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getEventByIdUseCase: GetEventByIdUseCase,
    private val getChineseZodiacUseCase: GetChineseZodiacUseCase,
    private val getWesternZodiacUseCase: GetWesternZodiacUseCase,
    private val getStatusWesternZodiacUseCase: GetStatusWesternZodiacUseCase,
    private val getStatusChineseZodiacUseCase: GetStatusChineseZodiacUseCase,
    private val deleteEventUseCase: DeleteEventUseCase
) : ViewModel() {

    private val _eventDetailState = MutableStateFlow(EventDetailState())
    private val _eventById = _eventDetailState.asStateFlow().flatMapLatest {
        getEventByIdUseCase.invoke(it.event.id)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = null
    )

    val eventDetailState = combine(
        _eventDetailState,
        _eventById
    ) { eventDetailState, eventById ->
        if (eventById != null){
            eventDetailState.copy(
                event = eventById
            )
        } else eventDetailState
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = EventDetailState()
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val eventId = savedStateHandle.get<Long>(EVENT_ID_KEY)
            val localDate = LocalDate.now()

            val gotEvent =
                (if (eventId != null) getEventByIdUseCase.invoke(eventId).first() else null)

            val statusChineseZodiac = getStatusChineseZodiacUseCase.invoke().first()
            val statusWesternZodiac = getStatusWesternZodiacUseCase.invoke().first()

            _eventDetailState.update {
                it.copy(
                    event = gotEvent ?: Event(
                        id = 1,
                        nameContact = "UNKNOWN",
                        originalDate = localDate,
                        yearMatter = true
                    ),
                    westernZodiac = getWesternZodiacUseCase(
                        date = if (gotEvent != null) gotEvent.originalDate else localDate
                    ),
                    chineseZodiac = getChineseZodiacUseCase(
                        date = if (gotEvent != null) gotEvent.originalDate else localDate
                    ),
                    statusChineseZodiac = statusChineseZodiac,
                    statusWesternZodiac = statusWesternZodiac
                )
            }
        }
    }


    fun onEvent(event: DetailInfoEvent){
        when(event){
            DetailInfoEvent.DeleteEvent -> {
                viewModelScope.launch {
                    deleteEventUseCase.invoke(idEvent = _eventDetailState.value.event.id)
                }
            }

            DetailInfoEvent.OnCloseNotesDialog -> {
                _eventDetailState.update { it.copy(
                    isShowNotesDialog = false
                ) }
            }

            DetailInfoEvent.OnShowNotesDialog -> {
                _eventDetailState.update { it.copy(
                    isShowNotesDialog = true,
                    newNotes = _eventDetailState.value.event.notes ?: ""
                ) }
            }

            DetailInfoEvent.SaveNewNotes -> {
                val event = _eventDetailState.value.event

                _eventDetailState.update { it.copy(
                    event = Event(
                        id = event.id,
                        eventType = event.eventType,
                        sortTypeEvent = event.sortTypeEvent,
                        nameContact = event.nameContact,
                        surnameContact = event.surnameContact,
                        originalDate = event.originalDate,
                        yearMatter = event.yearMatter,
                        notes = _eventDetailState.value.newNotes,
                        image = event.image
                    )
                ) }
            }

            DetailInfoEvent.OnCloseDeleteDialog -> {
                _eventDetailState.update { it.copy(
                    isShowDeleteDialog = false
                ) }
            }

            DetailInfoEvent.OnShowDeleteDialog -> {
                _eventDetailState.update { it.copy(
                    isShowDeleteDialog = true
                ) }
            }


        }
    }

}
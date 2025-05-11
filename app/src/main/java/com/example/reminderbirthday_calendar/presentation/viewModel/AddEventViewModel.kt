package com.example.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.event.EventType
import com.example.domain.useCase.calendar.event.UpsertEventUseCase
import com.example.domain.useCase.settings.notification.ScheduleAllEventsUseCase
import com.example.reminderbirthday_calendar.presentation.event.AddEvent
import com.example.reminderbirthday_calendar.presentation.sharedFlow.AddEventSharedFlow
import com.example.reminderbirthday_calendar.presentation.sharedFlow.AddEventSharedFlow.ShowToast
import com.example.reminderbirthday_calendar.presentation.state.AddEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val upsertEventUseCase: UpsertEventUseCase,
    private val scheduleAllEventsUseCase: ScheduleAllEventsUseCase
) : ViewModel() {
    private val _addEventState = MutableStateFlow(AddEventState())

    val addEventState = _addEventState.asStateFlow()
        .map { state ->
            if (state.valueName.trim().isEmpty()) {
                state.copy(isEnableAddEventButton = false)
            }
            else if(state.date == null){
                state.copy(isEnableAddEventButton = false)
            }
            else if(state.date.year > LocalDate.now().year){
                state.copy(isEnableAddEventButton = false)
            }
            else if (state.date.year == LocalDate.now().year && state.date.dayOfYear > LocalDate.now().dayOfYear){
                state.copy(isEnableAddEventButton = false)
            }
            else {
                state.copy(isEnableAddEventButton = true)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = AddEventState()
        )

    private val _addEventSharedFlow = MutableSharedFlow<AddEventSharedFlow>()
    val addEventSharedFlow = _addEventSharedFlow.asSharedFlow()

    fun onEvent(event: AddEvent) {
        when (event) {
            is AddEvent.ChangeDate -> {
                _addEventState.update {
                    it.copy(
                        date = event.date
                    )
                }
            }

            is AddEvent.ChangeEventType -> {
                _addEventState.update {
                    it.copy(
                        eventType = event.eventType
                    )
                }
            }

            is AddEvent.ChangeNotes -> {
                _addEventState.update {
                    it.copy(
                        notes = event.notes
                    )
                }
            }

            is AddEvent.ChangeValueName -> {
                _addEventState.update {
                    it.copy(
                        valueName = event.name
                    )
                }
            }

            is AddEvent.ChangeValueSurname -> {
                _addEventState.update {
                    it.copy(
                        valueSurname = event.surname
                    )
                }
            }

            AddEvent.ChangeYearMatter -> {
                _addEventState.update {
                    it.copy(
                        yearMatter = !it.yearMatter
                    )
                }
            }

            AddEvent.CloseDatePickerDialog -> {
                _addEventState.update {
                    it.copy(
                        isShowDatePicker = false
                    )
                }
            }

            is AddEvent.OnPickPhoto -> {
                _addEventState.update {
                    it.copy(
                        pickedPhoto = event.photo
                    )
                }
            }

            AddEvent.ShowDatePickerDialog -> {
                _addEventState.update {
                    it.copy(
                        isShowDatePicker = true
                    )
                }
            }

            AddEvent.AddEventButton -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val isSuccess = upsertEventUseCase.invoke(
                        eventType = _addEventState.value.eventType,
                        sortTypeEvent = _addEventState.value.sortType,
                        nameContact = _addEventState.value.valueName,
                        surnameContact = _addEventState.value.valueSurname,
                        originalDate = _addEventState.value.date!!,
                        yearMatter = _addEventState.value.yearMatter,
                        notes = _addEventState.value.notes,
                        image = _addEventState.value.pickedPhoto
                    )

                    if (isSuccess) {
                        scheduleAllEventsUseCase()
                        _addEventSharedFlow.emit(
                            value = ShowToast(
                                message = "Successfully added " +
                                    EventType.BIRTHDAY.name.lowercase() + " event"
                            )
                        )
                    } else {
                        _addEventSharedFlow.emit(
                            value = ShowToast(
                                message = "Error added " +
                                    EventType.BIRTHDAY.name.lowercase() + " event"
                            )
                        )
                    }
                }
            }

            is AddEvent.ChangeSortType -> {
                _addEventState.update {
                    it.copy(
                        sortType = event.sortType
                    )
                }
            }


        }
    }
}
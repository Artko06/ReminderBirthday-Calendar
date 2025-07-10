package com.example.reminderbirthday_calendar.presentation.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.calendar.contact.ImportContactsUseCase
import com.example.domain.useCase.calendar.event.AddEventToContactAppUseCase
import com.example.domain.useCase.calendar.event.UpsertEventUseCase
import com.example.domain.useCase.settings.notification.ScheduleAllEventsUseCase
import com.example.reminderbirthday_calendar.R
import com.example.reminderbirthday_calendar.presentation.event.AddEvent
import com.example.reminderbirthday_calendar.presentation.sharedFlow.AddEventSharedFlow
import com.example.reminderbirthday_calendar.presentation.sharedFlow.AddEventSharedFlow.ShowToast
import com.example.reminderbirthday_calendar.presentation.state.AddEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val upsertEventUseCase: UpsertEventUseCase,
    private val addEventToContactAppUseCase: AddEventToContactAppUseCase,
    private val scheduleAllEventsUseCase: ScheduleAllEventsUseCase,
    private val importContactsUseCase: ImportContactsUseCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private val _addEventState = MutableStateFlow(AddEventState())
    private val _isAddButtonEnabled = _addEventState.asStateFlow()
        .map { state ->
            if (state.valueName.trim().isEmpty()) false
            else if (state.date == null) false
            else if (state.yearMatter && state.date.year > LocalDate.now().year) false
            else if (state.yearMatter &&
                state.date.year == LocalDate.now().year &&
                state.date.dayOfYear > LocalDate.now().dayOfYear
            ) false
            else true
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )

    val addEventState = combine(
        _addEventState,
        _isAddButtonEnabled
    ) { addEventState, isAddButtonEnabled ->
        addEventState.copy(
            isEnableAddEventButton = isAddButtonEnabled
        )
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
                        idContact = _addEventState.value.idSelectedContact,
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
                        if (!_addEventState.value.idSelectedContact.isNullOrBlank() &&
                            _addEventState.value.readNameContact == _addEventState.value.valueName &&
                            _addEventState.value.readSurnameContact == _addEventState.value.valueSurname
                        ) {
                            val isSuccessWriteToContact = addEventToContactAppUseCase.invoke(
                                contactId = _addEventState.value.idSelectedContact!!,
                                eventDate = _addEventState.value.date!!,
                                eventType = _addEventState.value.eventType,
                                yearMatter = _addEventState.value.yearMatter,
                                customLabel = _addEventState.value.notes
                            )

                            println("isSuccessWriteToContact: $isSuccessWriteToContact")
                        }
                        _addEventSharedFlow.emit(
                            value = ShowToast(
                                messageResId = R.string.event_add_success,
                            )
                        )
                    } else {
                        _addEventSharedFlow.emit(
                            value = ShowToast(
                                messageResId = R.string.event_add_error,
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

            AddEvent.CloseListContacts -> {
                _addEventState.update {
                    it.copy(
                        isShowListContacts = false,
                        isLoadingContactList = false
                    )
                }
            }

            is AddEvent.OnSelectContact -> {
                _addEventState.update {
                    it.copy(
                        idSelectedContact = event.contact.id,
                        readNameContact = event.contact.name,
                        readSurnameContact = event.contact.surname,
                        valueName = event.contact.name,
                        valueSurname = event.contact.surname,
                        pickedPhoto = event.contact.image,
                        isShowListContacts = false,
                        isLoadingContactList = false
                    )
                }
            }

            AddEvent.ShowListContacts -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _addEventState.update {
                        it.copy(
                            isLoadingContactList = true
                        )
                    }

                    val permissionReadState =
                        ContextCompat.checkSelfPermission(
                            appContext,
                            Manifest.permission.READ_CONTACTS
                        ) ==
                                PackageManager.PERMISSION_GRANTED

                    val contacts = if (permissionReadState) importContactsUseCase() else emptyList()

                    _addEventState.update {
                        it.copy(
                            listContacts = contacts,
                            isShowListContacts = true,
                            isLoadingContactList = false
                        )
                    }
                }
            }


        }
    }
}
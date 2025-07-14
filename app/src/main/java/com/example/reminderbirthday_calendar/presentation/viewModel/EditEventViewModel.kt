package com.example.reminderbirthday_calendar.presentation.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.calendar.contact.GetContactByIdUseCase
import com.example.domain.useCase.calendar.contact.ImportContactsUseCase
import com.example.domain.useCase.calendar.event.AddEventToContactAppUseCase
import com.example.domain.useCase.calendar.event.GetEventByIdUseCase
import com.example.domain.useCase.calendar.event.UpsertEventUseCase
import com.example.domain.useCase.settings.notification.ScheduleAllEventsUseCase
import com.example.reminderbirthday_calendar.presentation.event.EditEvent
import com.example.reminderbirthday_calendar.presentation.navigation.model.EVENT_ID_KEY
import com.example.reminderbirthday_calendar.presentation.state.EditEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditEventViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val upsertEventUseCase: UpsertEventUseCase,
    private val scheduleAllEventsUseCase: ScheduleAllEventsUseCase,
    private val importContactsUseCase: ImportContactsUseCase,
    private val getEventByIdUseCase: GetEventByIdUseCase,
    private val addEventToContactAppUseCase: AddEventToContactAppUseCase,
    private val getContactByIdUseCase: GetContactByIdUseCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private val _editEventState = MutableStateFlow(EditEventState())
    private val _isSaveButtonEnabled = _editEventState.asStateFlow()
        .map { state ->
            if (state.name.trim().isEmpty()) false
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

    val editEventState = combine(
        _editEventState,
        _isSaveButtonEnabled
    ) { editEventState, isSaveButtonEnabled ->
        editEventState.copy(
            isSaveButtonEnable = isSaveButtonEnabled
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = EditEventState()
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val eventId = savedStateHandle.get<Long>(EVENT_ID_KEY)

            val gotEvent =
                (if (eventId != null) getEventByIdUseCase.invoke(eventId).first() else null)

            if (gotEvent != null) {
                _editEventState.update {
                    it.copy(
                        id = gotEvent.id,
                        idContact = gotEvent.idContact,
                        eventType = gotEvent.eventType,
                        sortType = gotEvent.sortTypeEvent,
                        name = gotEvent.nameContact,
                        surname = gotEvent.surnameContact,
                        date = gotEvent.originalDate,
                        yearMatter = gotEvent.yearMatter,
                        pickedPhoto = gotEvent.image,
                        notes = gotEvent.notes
                    )
                }
            }
        }
    }

    fun onEvent(event: EditEvent) {
        when (event) {
            is EditEvent.ChangeDate -> {
                _editEventState.update {
                    it.copy(
                        date = event.date
                    )
                }
            }

            is EditEvent.ChangeSortType -> {
                _editEventState.update {
                    it.copy(
                        sortType = event.sortType
                    )
                }
            }

            is EditEvent.ChangeValueName -> {
                _editEventState.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is EditEvent.ChangeValueSurname -> {
                _editEventState.update {
                    it.copy(
                        surname = event.surname
                    )
                }
            }

            EditEvent.ChangeYearMatter -> {
                _editEventState.update {
                    it.copy(
                        yearMatter = !it.yearMatter
                    )
                }
            }

            EditEvent.CloseDatePickerDialog -> {
                _editEventState.update {
                    it.copy(
                        isShowDatePicker = false
                    )
                }
            }

            EditEvent.CloseListContacts -> {
                _editEventState.update {
                    it.copy(
                        isShowListContacts = false,
                        isLoadingContactList = false
                    )
                }
            }

            is EditEvent.OnPickPhoto -> {
                _editEventState.update {
                    it.copy(
                        pickedPhoto = event.photo
                    )
                }
            }

            is EditEvent.OnSelectContact -> {
                _editEventState.update {
                    it.copy(
                        idContact = event.contact.id,
                        name = event.contact.name,
                        surname = event.contact.surname,
                        pickedPhoto = event.contact.image,
                        isShowListContacts = false,
                        isLoadingContactList = false
                    )
                }
            }

            EditEvent.ShowDatePickerDialog -> {
                _editEventState.update {
                    it.copy(
                        isShowDatePicker = true
                    )
                }
            }

            EditEvent.ShowListContacts -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _editEventState.update {
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

                    _editEventState.update {
                        it.copy(
                            listContacts = contacts,
                            isShowListContacts = true,
                            isLoadingContactList = false
                        )
                    }
                }
            }

            EditEvent.SaveEventButton -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val isSuccess = upsertEventUseCase.invoke(
                        id = _editEventState.value.id,
                        idContact = _editEventState.value.idContact,
                        eventType = _editEventState.value.eventType,
                        sortTypeEvent = _editEventState.value.sortType,
                        nameContact = _editEventState.value.name,
                        surnameContact = _editEventState.value.surname,
                        originalDate = _editEventState.value.date,
                        yearMatter = _editEventState.value.yearMatter,
                        notes = _editEventState.value.notes,
                        image = _editEventState.value.pickedPhoto
                    )

                    if (isSuccess) {
                        scheduleAllEventsUseCase()

                        if (!_editEventState.value.idContact.isNullOrBlank()) {
                            val importContact = getContactByIdUseCase
                                .invoke(contactId = _editEventState.value.idContact!!)

                            if (importContact.name == _editEventState.value.name &&
                                importContact.surname == _editEventState.value.surname
                            ) {
                                addEventToContactAppUseCase.invoke(
                                    contactId = _editEventState.value.idContact!!,
                                    eventDate = _editEventState.value.date,
                                    eventType = _editEventState.value.eventType,
                                    yearMatter = _editEventState.value.yearMatter,
                                    customLabel = null
                                )
                            }
                        }
                    }
                }
            }


        }
    }
}
package com.example.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.settings.notification.CancelNotifyAllEventUseCase
import com.example.domain.useCase.settings.notification.GetAllNotificationEventUseCase
import com.example.domain.useCase.settings.notification.ScheduleAllEventsUseCase
import com.example.domain.useCase.settings.notification.UpsertAllNotificationEventsUseCase
import com.example.domain.useCase.settings.notification.UpsertNotificationEventUseCase
import com.example.reminderbirthday_calendar.presentation.event.TimeReminderEvent
import com.example.reminderbirthday_calendar.presentation.state.TimeReminderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeReminderViewModel @Inject constructor(
    private val getAllNotificationEventUseCase: GetAllNotificationEventUseCase,
    private val upsertNotificationEventUseCase: UpsertNotificationEventUseCase,
    private val upsertAllNotificationEventsUseCase: UpsertAllNotificationEventsUseCase,

    private val scheduleAllEventsUseCase: ScheduleAllEventsUseCase,
    private val cancelNotifyAllEventUseCase: CancelNotifyAllEventUseCase,
): ViewModel() {
    private val _timeReminderState = MutableStateFlow(TimeReminderState())
    val timeReminderState = _timeReminderState.asStateFlow()

    init {
        viewModelScope.launch {
            val listTimeReminder = getAllNotificationEventUseCase.invoke().first()

            _timeReminderState.update { it.copy(
                listTimeReminder = listTimeReminder
            ) }
        }
    }

    fun onEvent(event: TimeReminderEvent){
        when(event){
            is TimeReminderEvent.EditNotificationEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    upsertNotificationEventUseCase.invoke(notificationEvent = event.notificationEvent)

                    updateListTimeReminder()
                    scheduleAllEventsUseCase()
                }
            }

            is TimeReminderEvent.ChangeStatusByIdNotificationEvent -> {
                val updatedList = _timeReminderState.value.listTimeReminder.map { notification ->
                    if (notification.id == event.id) {
                        notification.copy(statusOn = !notification.statusOn)
                    } else notification
                }

                _timeReminderState.update { it.copy(
                    listTimeReminder = updatedList
                ) }
            }

            is TimeReminderEvent.ChangeDaysBeforeNotificationEvent -> {
                val updatedList = _timeReminderState.value.listTimeReminder.map { notification ->
                    if (notification.id == event.notificationEvent.id){
                        notification.copy(daysBeforeEvent = event.notificationEvent.daysBeforeEvent)
                    } else notification
                }

                _timeReminderState.update { it.copy(
                    listTimeReminder = updatedList
                ) }
            }

            is TimeReminderEvent.ChangeHourMinuteNotificationEvent -> {
                val updatedList = _timeReminderState.value.listTimeReminder.map { notification ->
                    if (notification.id == event.notificationEvent.id){
                        notification.copy(
                            hour = event.notificationEvent.hour,
                            minute = event.notificationEvent.minute
                        )
                    } else notification
                }

                _timeReminderState.update { it.copy(
                    listTimeReminder = updatedList
                ) }
            }

            TimeReminderEvent.CloseEditTimeReminderDialog -> {
                _timeReminderState.update { it.copy(
                    isShowEditTimeReminderDialog = false
                ) }
            }

            TimeReminderEvent.ShowEditTimeReminderDialog -> {
                _timeReminderState.update { it.copy(
                    isShowEditTimeReminderDialog = true
                ) }
            }

            TimeReminderEvent.SaveButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val listTimeReminder = _timeReminderState.value.listTimeReminder

                    upsertAllNotificationEventsUseCase.invoke(notificationEvents = listTimeReminder)

                    cancelNotifyAllEventUseCase()
                    scheduleAllEventsUseCase()
                }
            }

            TimeReminderEvent.CloseTimePickerDialog -> {
                _timeReminderState.update { it.copy(
                    isShowTimePickerDialog = false,
                    currentNotificationForEdit = null
                ) }
            }
            is TimeReminderEvent.ShowTimePickerDialog -> {
                _timeReminderState.update { it.copy(
                    isShowTimePickerDialog = true,
                    currentNotificationForEdit = event.notificationEvent
                ) }
            }
        }
    }

    private fun updateListTimeReminder(){
        viewModelScope.launch(Dispatchers.IO) {
            val listTimeReminder = getAllNotificationEventUseCase.invoke().first()

            _timeReminderState.update { it.copy(
                listTimeReminder = listTimeReminder
            ) }
        }
    }
}
package com.example.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.settings.notification.SetStatusNotificationUseCase
import com.example.reminderbirthday_calendar.presentation.event.NotificationPermEvent
import com.example.reminderbirthday_calendar.presentation.state.NotificationPermState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationPermViewModel @Inject constructor(
    private val setStatusNotificationUseCase: SetStatusNotificationUseCase
) : ViewModel() {

    private val _notificationState = MutableStateFlow(NotificationPermState())
    val notificationState = _notificationState.asStateFlow()

    fun onEvent(event: NotificationPermEvent) {
        when (event) {
            NotificationPermEvent.OnRequestNotificationPermission -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _notificationState.update { it.copy(
                        requestPermission = true
                    ) }
                }
            }

            is NotificationPermEvent.OnChangeNotificationPermission -> {
                _notificationState.update { it.copy(
                    isPermissionGranted = event.isGranted
                ) }

                viewModelScope.launch(Dispatchers.IO) {
                    setStatusNotificationUseCase(activeStatus = event.isGranted)
                }
            }
        }
    }

}
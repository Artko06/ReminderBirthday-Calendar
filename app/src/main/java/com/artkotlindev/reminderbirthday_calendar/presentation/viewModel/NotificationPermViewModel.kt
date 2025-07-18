package com.artkotlindev.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.artkotlindev.reminderbirthday_calendar.presentation.event.NotificationPermEvent
import com.artkotlindev.reminderbirthday_calendar.presentation.state.NotificationPermState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotificationPermViewModel @Inject constructor(

) : ViewModel() {

    private val _notificationState = MutableStateFlow(NotificationPermState())
    val notificationState = _notificationState.asStateFlow()

    fun onEvent(event: NotificationPermEvent) {
        when (event) {
            NotificationPermEvent.OnRequestNotificationPermission -> {
                _notificationState.update { it.copy(
                    requestPermission = true
                ) }
            }
        }
    }
}
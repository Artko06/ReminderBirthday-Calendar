package com.artkotlindev.reminderbirthday_calendar.presentation.state

import com.artkotlindev.domain.models.notification.NotificationEvent

data class TimeReminderState(
    val listTimeReminder: List<NotificationEvent> = emptyList(),
    val currentNotificationForEdit: NotificationEvent? = null,

    val isShowEditTimeReminderDialog: Boolean = false,

    val isShowTimePickerDialog: Boolean = false
)
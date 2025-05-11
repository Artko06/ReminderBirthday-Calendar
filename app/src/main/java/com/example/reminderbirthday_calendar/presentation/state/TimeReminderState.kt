package com.example.reminderbirthday_calendar.presentation.state

import com.example.domain.models.notification.NotificationEvent

data class TimeReminderState(
    val listTimeReminder: List<NotificationEvent> = emptyList(),

    val isShowEditTimeReminderDialog: Boolean = false
)
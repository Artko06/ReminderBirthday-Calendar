package com.example.reminderbirthday_calendar.presentation.event

import com.example.domain.models.notification.NotificationEvent

sealed class TimeReminderEvent {
    data class EditNotificationEvent(val notificationEvent: NotificationEvent): TimeReminderEvent()
    data class ChangeStatusByIdNotificationEvent(val id: Int): TimeReminderEvent()
    object SaveButtonClick: TimeReminderEvent()

    object ShowEditTimeReminderDialog: TimeReminderEvent()
    object CloseEditTimeReminderDialog: TimeReminderEvent()
}
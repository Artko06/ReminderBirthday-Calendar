package com.example.reminderbirthday_calendar.presentation.event

import com.example.domain.models.notification.NotificationEvent

sealed class TimeReminderEvent {
    data class EditNotificationEvent(val notificationEvent: NotificationEvent): TimeReminderEvent()
    data class ChangeStatusByIdNotificationEvent(val id: Int): TimeReminderEvent()
    data class ChangeDaysBeforeNotificationEvent(val notificationEvent: NotificationEvent): TimeReminderEvent()
    object SaveButtonClick: TimeReminderEvent()

    object ShowEditTimeReminderDialog: TimeReminderEvent()
    object CloseEditTimeReminderDialog: TimeReminderEvent()

    data class ShowTimePickerDialog(val notificationEvent: NotificationEvent): TimeReminderEvent()
    object CloseTimePickerDialog: TimeReminderEvent()
    data class ChangeHourMinuteNotificationEvent(val notificationEvent: NotificationEvent): TimeReminderEvent()
}
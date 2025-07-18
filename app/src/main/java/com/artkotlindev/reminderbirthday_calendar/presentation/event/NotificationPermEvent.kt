package com.artkotlindev.reminderbirthday_calendar.presentation.event

sealed class NotificationPermEvent{
    object OnRequestNotificationPermission: NotificationPermEvent()
}

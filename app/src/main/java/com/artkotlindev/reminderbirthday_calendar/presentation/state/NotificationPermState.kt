package com.artkotlindev.reminderbirthday_calendar.presentation.state

data class NotificationPermState(
    val isPermissionGranted: Boolean = false,
    val requestPermission: Boolean = false
)
package com.example.reminderbirthday_calendar.presentation.navigation.model

sealed class Screen(
    val route: String
) {
    object NotificationPermissionScreen: Screen(route = "NOTIFICATION_PERMISSION_SCREEN")
    object MainScreen: Screen(route = "MAIN_SCREEN")
    object AddEventScreen: Screen(route = "ADD_EVENT_SCREEN")
    object TimeReminderScreen: Screen(route = "TIME_REMINDER_SCREEN")
}
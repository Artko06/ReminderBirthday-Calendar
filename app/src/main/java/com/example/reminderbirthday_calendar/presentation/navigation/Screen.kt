package com.example.reminderbirthday_calendar.presentation.navigation

sealed class Screen(
    val route: String
) {
    object NotificationPermissionScreen: Screen(route = "NOTIFICATION_PERMISSION_SCREEN")
    object MainScreen: Screen(route = "MAIN_SCREEN")
}
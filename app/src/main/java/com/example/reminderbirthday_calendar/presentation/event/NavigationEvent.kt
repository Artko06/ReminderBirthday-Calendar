package com.example.reminderbirthday_calendar.presentation.event

sealed class NavigationEvent {
    object OnChangeIsFirstLaunch: NavigationEvent()
}
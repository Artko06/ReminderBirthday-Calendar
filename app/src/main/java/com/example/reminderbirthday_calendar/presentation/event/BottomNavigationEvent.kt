package com.example.reminderbirthday_calendar.presentation.event

sealed class BottomNavigationEvent {
    data class OnSelectScreen(val indexScreen: Int): BottomNavigationEvent()
}
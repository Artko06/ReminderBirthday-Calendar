package com.artkotlindev.reminderbirthday_calendar.presentation.navigation.model

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter
)
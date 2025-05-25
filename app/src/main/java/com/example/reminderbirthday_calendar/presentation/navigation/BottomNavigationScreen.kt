package com.example.reminderbirthday_calendar.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R
import com.example.reminderbirthday_calendar.presentation.components.main.BottomNavigationBar
import com.example.reminderbirthday_calendar.presentation.event.BottomNavigationEvent
import com.example.reminderbirthday_calendar.presentation.navigation.model.BottomNavigationItem
import com.example.reminderbirthday_calendar.presentation.navigation.model.NumberBottomScreen
import com.example.reminderbirthday_calendar.presentation.screens.CalendarScreen
import com.example.reminderbirthday_calendar.presentation.screens.EventsScreen
import com.example.reminderbirthday_calendar.presentation.screens.SettingsScreen
import com.example.reminderbirthday_calendar.presentation.viewModel.BottomNavigationViewModel

@Composable
fun BottomNavigationScreen(
    onNavigateToAddEventScreen: () -> Unit,
    onNavigateToTimeReminderScreen: () -> Unit,
    onNavigateToEventDetailScreen: (Long) -> Unit,
    navigationViewModel: BottomNavigationViewModel = hiltViewModel()
){
    val state = navigationViewModel.bottomNavigationState.collectAsState().value

    val bottomItems = listOf<BottomNavigationItem>(
        BottomNavigationItem(
            title = LocalizedContext.current.getString(R.string.navbar_events),
            selectedIcon = Icons.Filled.Cake,
            unselectedIcon = Icons.Outlined.Cake
        ),
        BottomNavigationItem(
            title = LocalizedContext.current.getString(R.string.navbar_calendar),
            selectedIcon = Icons.Filled.CalendarMonth,
            unselectedIcon = Icons.Outlined.CalendarMonth
        ),
        BottomNavigationItem(
            title = LocalizedContext.current.getString(R.string.navbar_settings),
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar{
                BottomNavigationBar(
                    items = bottomItems,
                    selectedNavItem = state.selectedIndexScreen,
                    onSelectNavItem = { newIndex ->
                        navigationViewModel.onEvent(event = BottomNavigationEvent.OnSelectScreen(newIndex))
                    }
                )
            }
        },
        floatingActionButton = {
            if (NumberBottomScreen.entries[state.selectedIndexScreen] == NumberBottomScreen.EVENTS){
                FloatingActionButton(
                    onClick = {
                        onNavigateToAddEventScreen()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.PlaylistAdd,
                        contentDescription = "Add events"
                    )
                }
            }
        }
    ) { paddingValues ->
        when(NumberBottomScreen.entries[state.selectedIndexScreen]){
            NumberBottomScreen.EVENTS -> EventsScreen(
                modifier = Modifier.padding(paddingValues),
                onNavigateToEventDetailScreen = onNavigateToEventDetailScreen
            )
            NumberBottomScreen.CALENDAR -> CalendarScreen(
                modifier = Modifier.padding(paddingValues),
                onNavigateToEventDetailScreen = onNavigateToEventDetailScreen
            )
            NumberBottomScreen.SETTINGS -> SettingsScreen(
                onNavigateToTimeReminderScreen = onNavigateToTimeReminderScreen,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}
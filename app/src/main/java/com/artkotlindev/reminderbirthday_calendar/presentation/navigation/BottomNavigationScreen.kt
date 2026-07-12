package com.artkotlindev.reminderbirthday_calendar.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.artkotlindev.domain.util.extensionFunc.isNewYearRange
import com.artkotlindev.reminderbirthday_calendar.LocalizedContext
import com.artkotlindev.reminderbirthday_calendar.R
import com.artkotlindev.reminderbirthday_calendar.presentation.components.main.BottomNavigationBar
import com.artkotlindev.reminderbirthday_calendar.presentation.components.snowflake.Snowflake
import com.artkotlindev.reminderbirthday_calendar.presentation.event.BottomNavigationEvent
import com.artkotlindev.reminderbirthday_calendar.presentation.navigation.model.BottomNavigationItem
import com.artkotlindev.reminderbirthday_calendar.presentation.navigation.model.NumberBottomScreen
import com.artkotlindev.reminderbirthday_calendar.presentation.screens.CalendarScreen
import com.artkotlindev.reminderbirthday_calendar.presentation.screens.EventsScreen
import com.artkotlindev.reminderbirthday_calendar.presentation.screens.SettingsScreen
import com.artkotlindev.reminderbirthday_calendar.presentation.viewModel.BottomNavigationViewModel
import com.artkotlindev.reminderbirthday_calendar.presentation.viewModel.PreferencesViewModel

@Composable
fun BottomNavigationScreen(
    onNavigateToAddEventScreen: () -> Unit,
    onNavigateToTimeReminderScreen: () -> Unit,
    onNavigateToEventDetailScreen: (Long) -> Unit,
    navigationViewModel: BottomNavigationViewModel = hiltViewModel()
){
    val state = navigationViewModel.bottomNavigationState.collectAsState().value
    val preferencesViewModel: PreferencesViewModel = hiltViewModel()

    val bottomItems = listOf<BottomNavigationItem>(
        BottomNavigationItem(
            title = LocalizedContext.current.getString(R.string.navbar_events),
            selectedIcon = painterResource(R.drawable.ic_cake_fill),
            unselectedIcon = painterResource(R.drawable.ic_cake)
        ),
        BottomNavigationItem(
            title = LocalizedContext.current.getString(R.string.navbar_calendar),
            selectedIcon = painterResource(R.drawable.ic_calendar_month_fill),
            unselectedIcon = painterResource(R.drawable.ic_calendar_month)
        ),
        BottomNavigationItem(
            title = LocalizedContext.current.getString(R.string.navbar_settings),
            selectedIcon = painterResource(R.drawable.ic_settings_fill),
            unselectedIcon = painterResource(R.drawable.ic_settings)
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
                        painter = painterResource(R.drawable.ic_playlist_add),
                        contentDescription = "Add events"
                    )
                }
            }
        }
    ) { paddingValues ->

        if (state.onSnowflake && isNewYearRange()) {
            Snowflake(
                modifier = Modifier.fillMaxSize(),
                density = 3,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

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
                preferencesViewModel = preferencesViewModel,
                onNavigateToTimeReminderScreen = onNavigateToTimeReminderScreen,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}
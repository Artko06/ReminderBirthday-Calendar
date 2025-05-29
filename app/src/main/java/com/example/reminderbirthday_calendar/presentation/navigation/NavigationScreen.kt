package com.example.reminderbirthday_calendar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reminderbirthday_calendar.presentation.event.NavigationEvent
import com.example.reminderbirthday_calendar.presentation.navigation.model.EVENT_ID_KEY
import com.example.reminderbirthday_calendar.presentation.navigation.model.Screen
import com.example.reminderbirthday_calendar.presentation.screens.AddEventScreen
import com.example.reminderbirthday_calendar.presentation.screens.EditEventScreen
import com.example.reminderbirthday_calendar.presentation.screens.EventDetailScreen
import com.example.reminderbirthday_calendar.presentation.screens.NotificationPermissionScreen
import com.example.reminderbirthday_calendar.presentation.screens.TimeReminderScreen
import com.example.reminderbirthday_calendar.presentation.viewModel.EditEventViewModel
import com.example.reminderbirthday_calendar.presentation.viewModel.EventDetailViewModel
import com.example.reminderbirthday_calendar.presentation.viewModel.NavigationViewModel

@Composable
fun NavigationScreen(
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val stateNavigation = navigationViewModel.navigationState.collectAsState().value

    if (stateNavigation.startDestinationRoute != null) {

        NavHost(
            navController = navController,
            startDestination = stateNavigation.startDestinationRoute
        ) {
            composable(route = Screen.NotificationPermissionScreen.route) {
                NotificationPermissionScreen(
                    onNavigateToMainScreen = {
                        navigationViewModel.onEvent(event = NavigationEvent.OnChangeIsFirstLaunch)

                        navController.navigate(Screen.MainScreen.route){
                            popUpTo(Screen.NotificationPermissionScreen.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(route = Screen.MainScreen.route) {
                BottomNavigationScreen(
                    onNavigateToAddEventScreen = {
                        navController.navigate(Screen.AddEventScreen.route)
                    },
                    onNavigateToTimeReminderScreen = {
                        navController.navigate(Screen.TimeReminderScreen.route)
                    },
                    onNavigateToEventDetailScreen = { eventId ->
                        navController.navigate(Screen.EventDetailScreen.passEventId(eventId))
                    }
                )
            }

            composable(route = Screen.AddEventScreen.route) {
                AddEventScreen(
                    onBackFromAddEventScreen = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = Screen.TimeReminderScreen.route) {
                TimeReminderScreen(
                    onBackFromTimeReminderScreen = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.EventDetailScreen.route,
                arguments = listOf(navArgument(EVENT_ID_KEY) { type = NavType.LongType })
            ){
                val eventDetailViewModel = hiltViewModel<EventDetailViewModel>()

                EventDetailScreen(
                    eventDetailViewModel = eventDetailViewModel,
                    onBackFromDetailScreen = {
                        navController.popBackStack()
                    },
                    onNavigateToEditEventScreen = { eventId ->
                        navController.navigate(Screen.EditEventScreen.passEventId(eventId))
                    }
                )
            }

            composable(
                route = Screen.EditEventScreen.route,
                arguments = listOf(navArgument(EVENT_ID_KEY) { type = NavType.LongType })
            ) {
                val editEventViewModel = hiltViewModel<EditEventViewModel>()

                EditEventScreen(
                    editEventViewModel = editEventViewModel,
                    onBackFromEditEventScreen = {
                        navController.popBackStack()
                    }
                )
            }


        }
    }

}
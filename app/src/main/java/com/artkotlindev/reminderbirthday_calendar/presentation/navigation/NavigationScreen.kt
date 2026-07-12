package com.artkotlindev.reminderbirthday_calendar.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.artkotlindev.reminderbirthday_calendar.presentation.event.NavigationEvent
import com.artkotlindev.reminderbirthday_calendar.presentation.navigation.model.EVENT_ID_KEY
import com.artkotlindev.reminderbirthday_calendar.presentation.navigation.model.Screen
import com.artkotlindev.reminderbirthday_calendar.presentation.screens.AddEventScreen
import com.artkotlindev.reminderbirthday_calendar.presentation.screens.EditEventScreen
import com.artkotlindev.reminderbirthday_calendar.presentation.screens.EventDetailScreen
import com.artkotlindev.reminderbirthday_calendar.presentation.screens.NotificationPermissionScreen
import com.artkotlindev.reminderbirthday_calendar.presentation.screens.TimeReminderScreen
import com.artkotlindev.reminderbirthday_calendar.presentation.viewModel.EditEventViewModel
import com.artkotlindev.reminderbirthday_calendar.presentation.viewModel.EventDetailViewModel
import com.artkotlindev.reminderbirthday_calendar.presentation.viewModel.NavigationViewModel

const val ANIMATION_SPEC = 300

@Composable
fun NavigationScreen(
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val stateNavigation = navigationViewModel.navigationState.collectAsState().value

    if (stateNavigation.startDestinationRoute != null) {

        NavHost(
            navController = navController,
            startDestination = stateNavigation.startDestinationRoute,
            enterTransition = {
                fadeIn(animationSpec = tween(ANIMATION_SPEC))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(ANIMATION_SPEC))
            }
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
                        navController.navigateUp()
                    }
                )
            }

            composable(route = Screen.TimeReminderScreen.route) {
                TimeReminderScreen(
                    onBackFromTimeReminderScreen = {
                        navController.navigateUp()
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
                        navController.navigateUp()
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
                        navController.navigateUp()
                    }
                )
            }


        }
    }

}
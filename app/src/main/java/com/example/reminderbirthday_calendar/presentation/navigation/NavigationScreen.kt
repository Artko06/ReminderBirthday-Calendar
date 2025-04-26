package com.example.reminderbirthday_calendar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reminderbirthday_calendar.presentation.event.NavigationEvent
import com.example.reminderbirthday_calendar.presentation.navigation.model.Screen
import com.example.reminderbirthday_calendar.presentation.screens.NotificationPermissionScreen
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

                        navController.navigate(Screen.MainScreen.route)
                    }
                )
            }

            composable(route = Screen.MainScreen.route) {
                BottomNavigationScreen()
            }
        }
    }

}
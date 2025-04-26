package com.example.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.settings.firstLaunch.GetIsFirstLaunchUseCase
import com.example.domain.useCase.settings.firstLaunch.SetIsFirstLaunchUseCase
import com.example.reminderbirthday_calendar.presentation.event.NavigationEvent
import com.example.reminderbirthday_calendar.presentation.navigation.Screen
import com.example.reminderbirthday_calendar.presentation.state.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val getIsFirstLaunchUseCase: GetIsFirstLaunchUseCase,
    private val setIsFirstLaunchUseCase: SetIsFirstLaunchUseCase
): ViewModel() {
    private val _navigationState = MutableStateFlow(NavigationState())
    val navigationState = _navigationState.asStateFlow()

    init {
        viewModelScope.launch {
            val isFirstLaunch = getIsFirstLaunchUseCase().first()

            if (isFirstLaunch) {
                _navigationState.update { state ->
                    state.copy(
                        startDestinationRoute = Screen.NotificationPermissionScreen.route
                    )
                }
            } else {
                _navigationState.update { state ->
                    state.copy(
                        startDestinationRoute = Screen.MainScreen.route
                    )
                }
            }
        }
    }

    fun onEvent(event: NavigationEvent) {
        when(event){
            NavigationEvent.OnChangeIsFirstLaunch -> {
                viewModelScope.launch {
                    setIsFirstLaunchUseCase(isFirstLaunch = false)
                }
            }


        }
    }
}
package com.artkotlindev.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artkotlindev.domain.useCase.settings.snowflake.GetStatusSnowflakeUseCase
import com.artkotlindev.reminderbirthday_calendar.presentation.event.BottomNavigationEvent
import com.artkotlindev.reminderbirthday_calendar.presentation.state.BottomNavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BottomNavigationViewModel @Inject constructor(
    private val getStatusSnowflakeUseCase: GetStatusSnowflakeUseCase
): ViewModel() {
    private val _bottomNavigationState = MutableStateFlow(BottomNavigationState())

    private val _snowflakeState = getStatusSnowflakeUseCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val bottomNavigationState = combine(
        flow = _bottomNavigationState,
        flow2 = _snowflakeState
    ) { bottomNavigationState, snowflakeState ->

        bottomNavigationState.copy(
            onSnowflake = snowflakeState
        )
    }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BottomNavigationState()
    )

    fun onEvent(event: BottomNavigationEvent){
        when(event){
            is BottomNavigationEvent.OnSelectScreen -> {
                _bottomNavigationState.update{ it.copy(
                    selectedIndexScreen = event.indexScreen
                )
                }
            }


        }
    }
}
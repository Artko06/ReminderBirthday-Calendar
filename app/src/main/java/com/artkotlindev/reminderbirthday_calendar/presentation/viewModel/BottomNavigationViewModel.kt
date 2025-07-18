package com.artkotlindev.reminderbirthday_calendar.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.artkotlindev.reminderbirthday_calendar.presentation.event.BottomNavigationEvent
import com.artkotlindev.reminderbirthday_calendar.presentation.state.BottomNavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BottomNavigationViewModel @Inject constructor(

): ViewModel() {
    private val _bottomNavigationState = MutableStateFlow(BottomNavigationState())
    val bottomNavigationState = _bottomNavigationState.asStateFlow()

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
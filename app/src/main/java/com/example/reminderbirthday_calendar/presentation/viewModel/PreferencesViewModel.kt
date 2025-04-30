package com.example.reminderbirthday_calendar.presentation.viewModel

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.calendar.zodiac.status.GetStatusChineseZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.status.GetStatusWesternZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.status.SetStatusChineseZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.status.SetStatusWesternZodiacUseCase
import com.example.reminderbirthday_calendar.presentation.event.PreferencesEvent
import com.example.reminderbirthday_calendar.presentation.state.PreferencesState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val getStatusChineseZodiacUseCase: GetStatusChineseZodiacUseCase,
    private val setStatusChineseZodiacUseCase: SetStatusChineseZodiacUseCase,

    private val getStatusWesternZodiacUseCase: GetStatusWesternZodiacUseCase,
    private val setStatusWesternZodiacUseCase: SetStatusWesternZodiacUseCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private val _preferencesState = MutableStateFlow(PreferencesState())
    val preferencesState = _preferencesState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _preferencesState.update {
                it.copy(
                    isEnableWesternZodiac = getStatusWesternZodiacUseCase.invoke().first(),
                    isEnableChineseZodiac = getStatusChineseZodiacUseCase.invoke().first()
                )
            }
        }
    }

    fun onEvent(event: PreferencesEvent){
        when(event){
            PreferencesEvent.ChangeChineseZodiacStatus -> {
                _preferencesState.update { it.copy(
                    isEnableChineseZodiac = !it.isEnableChineseZodiac
                )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    setStatusChineseZodiacUseCase(activeStatus = _preferencesState.value.isEnableChineseZodiac)
                }
            }

            PreferencesEvent.ChangeWesternZodiacStatus -> {
                _preferencesState.update { it.copy(
                    isEnableWesternZodiac = !it.isEnableWesternZodiac
                )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    setStatusWesternZodiacUseCase(activeStatus = _preferencesState.value.isEnableWesternZodiac)
                }
            }

            PreferencesEvent.ShowSettingsNotificationDialog -> {
                val isGrantedNotification = NotificationManagerCompat.from(appContext).areNotificationsEnabled()

               _preferencesState.update { it.copy(
                   isEnableStatusNotification = isGrantedNotification,
                   isShowSettingsNotificationDialog = true
               ) }
            }

            PreferencesEvent.CloseSettingsNotificationDialog -> {
                _preferencesState.update { it.copy(
                    isShowSettingsNotificationDialog = false
                ) }
            }
        }
    }


}
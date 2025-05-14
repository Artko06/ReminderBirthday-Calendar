package com.example.reminderbirthday_calendar.presentation.viewModel

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.calendar.zodiac.status.GetStatusChineseZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.status.GetStatusWesternZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.status.SetStatusChineseZodiacUseCase
import com.example.domain.useCase.calendar.zodiac.status.SetStatusWesternZodiacUseCase
import com.example.domain.useCase.settings.showTypeEvent.GetStatusShowAnniversaryEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.GetStatusShowBirthdayEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.GetStatusShowOtherEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.SetStatusShowAnniversaryEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.SetStatusShowBirthdayEventUseCase
import com.example.domain.useCase.settings.showTypeEvent.SetStatusShowOtherEventUseCase
import com.example.domain.useCase.settings.theme.GetThemeUseCase
import com.example.domain.useCase.settings.theme.SetThemeUseCase
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

    private val getStatusShowAnniversaryEventUseCase: GetStatusShowAnniversaryEventUseCase,
    private val getStatusShowBirthdayEventUseCase: GetStatusShowBirthdayEventUseCase,
    private val getStatusShowOtherEventUseCase: GetStatusShowOtherEventUseCase,
    private val setStatusShowAnniversaryEventUseCase: SetStatusShowAnniversaryEventUseCase,
    private val setStatusShowBirthdayEventUseCase: SetStatusShowBirthdayEventUseCase,
    private val setStatusShowOtherEventUseCase: SetStatusShowOtherEventUseCase,

    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,

    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private val _preferencesState = MutableStateFlow(PreferencesState())
    val preferencesState = _preferencesState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _preferencesState.update {
                it.copy(
                    isEnableWesternZodiac = getStatusWesternZodiacUseCase.invoke().first(),
                    isEnableChineseZodiac = getStatusChineseZodiacUseCase.invoke().first(),

                    isEnableShowBirthdayEvent = getStatusShowBirthdayEventUseCase.invoke().first(),
                    isEnableShowAnniversaryEvent = getStatusShowAnniversaryEventUseCase.invoke()
                        .first(),
                    isEnableShowOtherEvent = getStatusShowOtherEventUseCase.invoke().first()
                )
            }
        }
    }

    fun onEvent(event: PreferencesEvent) {
        when (event) {
            PreferencesEvent.ChangeChineseZodiacStatus -> {
                _preferencesState.update {
                    it.copy(
                        isEnableChineseZodiac = !it.isEnableChineseZodiac
                    )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    setStatusChineseZodiacUseCase(activeStatus = _preferencesState.value.isEnableChineseZodiac)
                }
            }

            PreferencesEvent.ChangeWesternZodiacStatus -> {
                _preferencesState.update {
                    it.copy(
                        isEnableWesternZodiac = !it.isEnableWesternZodiac
                    )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    setStatusWesternZodiacUseCase(activeStatus = _preferencesState.value.isEnableWesternZodiac)
                }
            }

            PreferencesEvent.ShowSettingsNotificationDialog -> {
                val isGrantedNotification =
                    NotificationManagerCompat.from(appContext).areNotificationsEnabled()

                _preferencesState.update {
                    it.copy(
                        isEnableStatusNotification = isGrantedNotification,
                        isShowSettingsNotificationDialog = true
                    )
                }
            }

            PreferencesEvent.CloseSettingsNotificationDialog -> {
                _preferencesState.update {
                    it.copy(
                        isShowSettingsNotificationDialog = false
                    )
                }
            }

            PreferencesEvent.ChangeStatusShowAnniversaryEvent -> {
                _preferencesState.update {
                    it.copy(
                        isEnableShowAnniversaryEvent = !it.isEnableShowAnniversaryEvent
                    )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    setStatusShowAnniversaryEventUseCase(activeStatus = _preferencesState.value.isEnableShowAnniversaryEvent)
                }
            }

            PreferencesEvent.ChangeStatusShowBirthdayEvent -> {
                _preferencesState.update {
                    it.copy(
                        isEnableShowBirthdayEvent = !it.isEnableShowBirthdayEvent
                    )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    setStatusShowBirthdayEventUseCase(activeStatus = _preferencesState.value.isEnableShowBirthdayEvent)
                }
            }

            PreferencesEvent.ChangeStatusShowOtherEvent -> {
                _preferencesState.update {
                    it.copy(
                        isEnableShowOtherEvent = !it.isEnableShowOtherEvent
                    )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    setStatusShowOtherEventUseCase(activeStatus = _preferencesState.value.isEnableShowOtherEvent)
                }
            }

            PreferencesEvent.ShowStatusTypeEventDialog -> {
                _preferencesState.update {
                    it.copy(
                        isShowStatusTypeEventsDialog = true
                    )
                }
            }

            PreferencesEvent.CloseStatusTypeEventDialog -> {
                _preferencesState.update {
                    it.copy(
                        isShowStatusTypeEventsDialog = false
                    )
                }
            }

            is PreferencesEvent.ChangeAppTheme -> {
                viewModelScope.launch {
                    setThemeUseCase.invoke(theme = event.theme)
                }
            }

            PreferencesEvent.CloseAppThemeDialog -> {
                _preferencesState.update { it.copy(
                    isShowAppThemeDialog = false
                ) }
            }
            PreferencesEvent.ShowAppThemeDialog -> {
                viewModelScope.launch {
                    val theme = getThemeUseCase.invoke().first()

                    _preferencesState.update { it.copy(
                        isShowAppThemeDialog = true,
                        selectedTheme = theme
                    ) }
                }
            }


        }
    }
}
package com.artkotlindev.reminderbirthday_calendar.presentation.viewModel

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artkotlindev.domain.useCase.calendar.zodiac.status.GetStatusChineseZodiacUseCase
import com.artkotlindev.domain.useCase.calendar.zodiac.status.GetStatusWesternZodiacUseCase
import com.artkotlindev.domain.useCase.calendar.zodiac.status.SetStatusChineseZodiacUseCase
import com.artkotlindev.domain.useCase.calendar.zodiac.status.SetStatusWesternZodiacUseCase
import com.artkotlindev.domain.useCase.settings.language.GetLanguageUseCase
import com.artkotlindev.domain.useCase.settings.language.SetLanguageUseCase
import com.artkotlindev.domain.useCase.settings.showTypeEvent.GetStatusShowAnniversaryEventUseCase
import com.artkotlindev.domain.useCase.settings.showTypeEvent.GetStatusShowBirthdayEventUseCase
import com.artkotlindev.domain.useCase.settings.showTypeEvent.GetStatusShowOtherEventUseCase
import com.artkotlindev.domain.useCase.settings.showTypeEvent.SetStatusShowAnniversaryEventUseCase
import com.artkotlindev.domain.useCase.settings.showTypeEvent.SetStatusShowBirthdayEventUseCase
import com.artkotlindev.domain.useCase.settings.showTypeEvent.SetStatusShowOtherEventUseCase
import com.artkotlindev.domain.useCase.settings.snowflake.GetStatusSnowflakeUseCase
import com.artkotlindev.domain.useCase.settings.snowflake.SetStatusSnowflakeUseCase
import com.artkotlindev.domain.useCase.settings.theme.GetThemeUseCase
import com.artkotlindev.domain.useCase.settings.theme.SetThemeUseCase
import com.artkotlindev.reminderbirthday_calendar.presentation.event.PreferencesEvent
import com.artkotlindev.reminderbirthday_calendar.presentation.state.PreferencesState
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
    private val getLanguageUseCase: GetLanguageUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,

    private val getStatusSnowflakeUseCase: GetStatusSnowflakeUseCase,
    private val setStatusSnowflakeUseCase: SetStatusSnowflakeUseCase,

    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private val _preferencesState = MutableStateFlow(PreferencesState())
    val preferencesState = _preferencesState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _preferencesState.update {
                it.copy(
                    isEnableZodiacSign = getStatusWesternZodiacUseCase.invoke().first(),
                    isEnableChineseZodiac = getStatusChineseZodiacUseCase.invoke().first(),

                    isEnableShowBirthdayEvent = getStatusShowBirthdayEventUseCase.invoke().first(),
                    isEnableShowAnniversaryEvent = getStatusShowAnniversaryEventUseCase.invoke()
                        .first(),
                    isEnableShowOtherEvent = getStatusShowOtherEventUseCase.invoke().first(),
                    onStatusSnowflake = getStatusSnowflakeUseCase.invoke().first()
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

            PreferencesEvent.ChangeZodiacSignStatus -> {
                _preferencesState.update {
                    it.copy(
                        isEnableZodiacSign = !it.isEnableZodiacSign
                    )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    setStatusWesternZodiacUseCase(activeStatus = _preferencesState.value.isEnableZodiacSign)
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
                    _preferencesState.update { it.copy(
                        selectedTheme = event.theme
                    )
                    }

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

            is PreferencesEvent.ChangeAppLanguage -> {
                viewModelScope.launch {
                    _preferencesState.update { it.copy(
                        selectedLanguage = event.language
                    )
                    }

                    setLanguageUseCase.invoke(language = event.language)
                }
            }

            PreferencesEvent.CloseAppLanguageDialog -> {
                _preferencesState.update { it.copy(
                    isShowAppLanguageDialog = false
                ) }
            }

            PreferencesEvent.ShowAppLanguageDialog -> {
                viewModelScope.launch {
                    val language = getLanguageUseCase.invoke().first()

                    _preferencesState.update { it.copy(
                        isShowAppLanguageDialog = true,
                        selectedLanguage = language
                    ) }
                }
            }

            PreferencesEvent.ChangeStatusSnowflake -> {
                _preferencesState.update {
                    it.copy(
                        onStatusSnowflake = !it.onStatusSnowflake
                    )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    setStatusSnowflakeUseCase(
                        statusSnowflake = _preferencesState.value.onStatusSnowflake
                    )
                }
            }


        }
    }
}
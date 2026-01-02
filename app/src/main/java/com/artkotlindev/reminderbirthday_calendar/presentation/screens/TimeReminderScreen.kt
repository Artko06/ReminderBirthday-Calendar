package com.artkotlindev.reminderbirthday_calendar.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.artkotlindev.reminderbirthday_calendar.LocalizedContext
import com.artkotlindev.reminderbirthday_calendar.R
import com.artkotlindev.reminderbirthday_calendar.presentation.components.dialogWindow.TimePickerDialog
import com.artkotlindev.reminderbirthday_calendar.presentation.components.notification.TimeReminderItem
import com.artkotlindev.reminderbirthday_calendar.presentation.event.TimeReminderEvent
import com.artkotlindev.reminderbirthday_calendar.presentation.viewModel.TimeReminderViewModel

@Composable
fun TimeReminderScreen(
    onBackFromTimeReminderScreen: () -> Unit,
    timeReminderViewModel: TimeReminderViewModel = hiltViewModel()
) {
    val stateTimeReminder = timeReminderViewModel.timeReminderState.collectAsState().value

    if (stateTimeReminder.isShowTimePickerDialog && stateTimeReminder.currentNotificationForEdit != null) {
        TimePickerDialog(
            notificationEvent = stateTimeReminder.currentNotificationForEdit,
            onDismiss = {
                timeReminderViewModel.onEvent(event = TimeReminderEvent.CloseTimePickerDialog)
            },
            onConfirm = { notification ->
                timeReminderViewModel.onEvent(event = TimeReminderEvent.ChangeHourMinuteNotificationEvent(notification))
            }
        )
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Outlined.Alarm,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.fillMaxSize()
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item(
                key = "Title"
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Alarm,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = LocalizedContext.current.getString(R.string.time_notifications),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.alignByBaseline()
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))
            }

            items(
                items = stateTimeReminder.listTimeReminder,
                key = { it.id }
            ) { notificationEvent ->
                TimeReminderItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    notificationEvent = notificationEvent,
                    changeStatusNotification = { id ->
                        timeReminderViewModel.onEvent(
                            event = TimeReminderEvent.ChangeStatusByIdNotificationEvent(
                                id
                            )
                        )
                    },
                    selectNewDaysBefore = { notification ->
                        timeReminderViewModel.onEvent(
                            event = TimeReminderEvent.ChangeDaysBeforeNotificationEvent(
                                notification
                            )
                        )
                    },
                    timeIconClick = { notification ->
                        timeReminderViewModel.onEvent(
                            event = TimeReminderEvent.ShowTimePickerDialog(
                                notification
                            )
                        )
                    }
                )
            }

            item(
                key = "Save_Button"
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            timeReminderViewModel.onEvent(event = TimeReminderEvent.SaveButtonClick)
                            onBackFromTimeReminderScreen()
                        }
                    ) {
                        Text(
                            text = LocalizedContext.current.getString(R.string.save)
                        )
                    }
                }
            }


        }
    }
}
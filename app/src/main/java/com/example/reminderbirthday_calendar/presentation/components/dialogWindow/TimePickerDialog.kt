package com.example.reminderbirthday_calendar.presentation.components.dialogWindow

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import com.example.domain.models.notification.NotificationEvent
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    notificationEvent: NotificationEvent,
    onDismiss: () -> Unit,
    onConfirm: (NotificationEvent) -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = notificationEvent.hour,
        initialMinute = notificationEvent.minute
    )

    AlertDialog(
        title = { Text(
            text = LocalizedContext.current.getString(R.string.time_notification)
        ) },
        text = {
            Column {
                TimePicker(
                    state = timePickerState,
                )
            }

        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(
                    notificationEvent.copy(
                        hour = timePickerState.hour,
                        minute = timePickerState.minute
                    )
                )

                onDismiss()
            }) {
                Text(
                    text = LocalizedContext.current.getString(R.string.save)
                )
            }
        },
    )
}
package com.example.reminderbirthday_calendar.presentation.components.dialogWindow

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun NotificationPermissionDialog(
    onConfirmButton: () -> Unit,
    onDismiss: () -> Unit,
    statusNotification: Boolean
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Permission for notifications")
        },
        text = {
            if (statusNotification) {
                Text(
                    text = buildAnnotatedString {
                        append("Notifications in the app are ")

                        // Выделенное слово
                        withStyle(style = SpanStyle(color = Color.Green, fontWeight = FontWeight.Bold)) {
                            append("turn on")
                        }

                        append(". Are you sure you want to turn off them? " +
                                "Permission to send notifications allows you to be notified about calendar events. ")

                        append("If notifications still don't arrive, check your settings")
                    }
                )
            } else{
                Text(
                    text = buildAnnotatedString {
                        append("Notifications in the app are ")

                        // Выделенное слово
                        withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                            append("turn off")
                        }

                        append(". We recommend turning on them. " +
                                "Permission to send notifications allows you to be notified about calendar events")
                    }
                )
            }

        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButton()
                onDismiss()
            }) {
                Text("To setting")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
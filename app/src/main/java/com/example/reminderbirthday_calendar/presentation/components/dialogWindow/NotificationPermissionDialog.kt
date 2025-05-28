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
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R

@Composable
fun NotificationPermissionDialog(
    onConfirmButton: () -> Unit,
    onDismiss: () -> Unit,
    statusNotification: Boolean
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = LocalizedContext.current.getString(R.string.title_notification_dialog)
            )
        },
        text = {
            Text(
                text = buildAnnotatedString {
                    append(LocalizedContext.current.getString(R.string.text_notification_dialog_part1) + " ")

                    if (statusNotification) {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Green,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(LocalizedContext.current.getString(R.string.text_notification_dialog_highlight_on))
                        }
                    } else{
                        withStyle(
                            style = SpanStyle(
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(LocalizedContext.current.getString(R.string.text_notification_dialog_highlight_off))
                        }
                    }

                    append(LocalizedContext.current.getString(R.string.text_notification_dialog_part2))
                }
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButton()
                onDismiss()
            }) {
                Text(
                    text = LocalizedContext.current.getString(R.string.to_settings)
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = LocalizedContext.current.getString(R.string.close)
                )
            }
        }
    )
}
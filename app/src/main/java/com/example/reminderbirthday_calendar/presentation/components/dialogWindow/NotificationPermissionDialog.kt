package com.example.reminderbirthday_calendar.presentation.components.settings

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
            Text("Разрешение на уведомления")
        },
        text = {
            if (statusNotification) {
                Text(
                    text = buildAnnotatedString {
                        append("Уведомления в приложении ")

                        // Выделенное слово
                        withStyle(style = SpanStyle(color = Color.Green, fontWeight = FontWeight.Bold)) {
                            append("включены")
                        }

                        append(", вы точно хотите их выключить? " +
                                "Разрешение на отправку уведомлений позволяет оповещать вас о событиях календаря. ")

                        append("Eсли уведомления всё равно не приходят, то проверте настройки")
                    }
                )
            } else{
                Text(
                    text = buildAnnotatedString {
                        append("Уведомления в приложении ")

                        // Выделенное слово
                        withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                            append("выключены")
                        }

                        append(", рекомендуем включить их. " +
                                "Разрешение на отправку уведомлений позволяет оповещать вас о событиях календаря")
                    }
                )
            }

        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButton()
                onDismiss()
            }) {
                Text("Перейти")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Закрыть")
            }
        }
    )
}
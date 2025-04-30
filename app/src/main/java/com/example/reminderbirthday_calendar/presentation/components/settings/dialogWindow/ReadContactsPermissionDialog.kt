package com.example.reminderbirthday_calendar.presentation.components.settings.dialogWindow

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ReadContactsPermissionDialog(
    onConfirmButton: () -> Unit,
    onDismiss: () -> Unit,
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Разрешение на чтение контактов")
        },
        text = {
            Text("Для чтения контактов из телефонной книги приложению необходимо разрешение")
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
package com.example.reminderbirthday_calendar.presentation.components.dialogWindow

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R

@Composable
fun ReadContactsPermissionDialog(
    onConfirmButton: () -> Unit,
    onDismiss: () -> Unit,
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = LocalizedContext.current.getString(R.string.perm_to_read_contact_title)
            )
        },
        text = {
            Text(
                text = LocalizedContext.current.getString(R.string.perm_to_read_contact_text)
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
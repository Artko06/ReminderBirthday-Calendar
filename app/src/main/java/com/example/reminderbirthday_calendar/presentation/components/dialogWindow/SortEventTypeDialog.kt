package com.example.reminderbirthday_calendar.presentation.components.dialogWindow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SortEventTypeDialog(
    onDismiss: () -> Unit,
    changeStatusShowBirthday: () -> Unit,
    changeStatusShowAnniversary: () -> Unit,
    changeStatusShowOther: () -> Unit,

    statusShowBirthday: Boolean,
    statusShowAnniversary: Boolean,
    statusShowOther: Boolean
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Select types") },
        text = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = true, onClick = { changeStatusShowBirthday() }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = statusShowBirthday, onCheckedChange = { changeStatusShowBirthday() })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Show Birthday event")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = true, onClick = { changeStatusShowAnniversary() }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = statusShowAnniversary, onCheckedChange = { changeStatusShowAnniversary() })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Show Anniversary event")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = true, onClick = { changeStatusShowOther() }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = statusShowOther, onCheckedChange = { changeStatusShowOther() })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Show Other event")
                }
            }
        },
        confirmButton = { },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
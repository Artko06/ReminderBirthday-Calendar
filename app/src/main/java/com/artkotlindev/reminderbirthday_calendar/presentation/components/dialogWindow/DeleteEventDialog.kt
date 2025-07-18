package com.artkotlindev.reminderbirthday_calendar.presentation.components.dialogWindow

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.artkotlindev.reminderbirthday_calendar.LocalizedContext
import com.artkotlindev.reminderbirthday_calendar.R

@Composable
fun DeleteEventDialog(
    onConfirmButton: () -> Unit,
    onDismiss: () -> Unit,
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = LocalizedContext.current.getString(R.string.deleting_event_title)
            )
        },
        text = {
            Text(
                text = buildAnnotatedString {
                    append(LocalizedContext.current.getString(R.string.deleting_event_text_part1) + " ")

                    withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                        append(LocalizedContext.current.getString(R.string.deleting_event_text_highlight))
                    }

                    append(" " + LocalizedContext.current.getString(R.string.deleting_event_text_part2))
                }
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButton()
                onDismiss()
            }) {
                Text(
                    text = LocalizedContext.current.getString(R.string.delete)
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
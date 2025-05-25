package com.example.reminderbirthday_calendar.presentation.components.dialogWindow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.event.EventType
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R

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
        title = { Text(
            text = LocalizedContext.current.getString(R.string.types_events_title_dialog)
        ) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                EventType.entries.forEach { type ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = true, onClick = {
                                when (type) {
                                    EventType.BIRTHDAY -> changeStatusShowBirthday()
                                    EventType.ANNIVERSARY -> changeStatusShowAnniversary()
                                    EventType.OTHER -> changeStatusShowOther()
                                }
                            }),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = when (type) {
                                EventType.BIRTHDAY -> statusShowBirthday
                                EventType.ANNIVERSARY -> statusShowAnniversary
                                EventType.OTHER -> statusShowOther
                            },
                            onCheckedChange = {
                                when (type) {
                                    EventType.BIRTHDAY -> changeStatusShowBirthday()
                                    EventType.ANNIVERSARY -> changeStatusShowAnniversary()
                                    EventType.OTHER -> changeStatusShowOther()
                                }
                            })
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = buildAnnotatedString {
                                append(LocalizedContext.current.getString(R.string.type_event_show_text) + " ")

                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Italic
                                    )
                                ) {
                                    append(when (type) {
                                        EventType.BIRTHDAY -> LocalizedContext.current.getString(R.string.type_events_1)
                                        EventType.ANNIVERSARY -> LocalizedContext.current.getString(
                                            R.string.type_events_2
                                        )
                                        EventType.OTHER -> LocalizedContext.current.getString(R.string.type_events_3)
                                    })
                                }
                            },
                            fontSize = 14.sp
                        )
                    }
                }
            }
        },
        confirmButton = { },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = LocalizedContext.current.getString(R.string.close)
                )
            }
        }
    )
}
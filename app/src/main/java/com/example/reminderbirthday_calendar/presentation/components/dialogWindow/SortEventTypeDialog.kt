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
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = true, onClick = { changeStatusShowBirthday() }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = statusShowBirthday, onCheckedChange = { changeStatusShowBirthday() })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("Show ")

                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 16.sp
                                )
                            ) {
                                append("Birthday")
                            }

                            append(" event")
                        },
                        fontSize = 14.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = true, onClick = { changeStatusShowAnniversary() }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = statusShowAnniversary, onCheckedChange = { changeStatusShowAnniversary() })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("Show ")

                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 16.sp
                                )
                            ) {
                                append("Anniversary")
                            }

                            append(" event")
                        },
                        fontSize = 14.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = true, onClick = { changeStatusShowOther() }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = statusShowOther, onCheckedChange = { changeStatusShowOther() })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("Show ")

                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 16.sp
                                )
                            ) {
                                append("Other")
                            }

                            append(" event")
                        },
                        fontSize = 14.sp
                    )
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
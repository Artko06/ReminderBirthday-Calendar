package com.example.reminderbirthday_calendar.presentation.components.dialogWindow

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onDismiss: () -> Unit,
    changeValueDatePicker: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(onClick = {
                val localDate = datePickerState.selectedDateMillis?.let { mills ->
                    Instant.ofEpochMilli(mills)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                }

                if (localDate != null) changeValueDatePicker(localDate)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {}
    ) {
        DatePicker(state = datePickerState)
    }

}
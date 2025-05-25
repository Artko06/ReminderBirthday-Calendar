package com.example.reminderbirthday_calendar.presentation.components.dialogWindow

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    initDate: LocalDate? = null,
    onDismiss: () -> Unit,
    changeValueDatePicker: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initDate
            ?.atStartOfDay(ZoneOffset.UTC)
            ?.toInstant()
            ?.toEpochMilli()
    )
    
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
                Text(
                    text = LocalizedContext.current.getString(R.string.select)
                )
            }
        },
        dismissButton = {}
    ) {
        DatePicker(state = datePickerState)
    }

}
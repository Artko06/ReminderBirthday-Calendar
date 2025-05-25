package com.example.reminderbirthday_calendar.presentation.components.dialogWindow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.models.settings.ThemeType
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R

@Composable
fun ThemeDialog(
    onDismiss: () -> Unit,
    selectedTheme: ThemeType,
    onSelectTheme: (ThemeType) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(
            text = LocalizedContext.current.getString(R.string.theme_title_dialog)
        ) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ThemeType.entries.forEach { theme ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = true, onClick = {
                                onSelectTheme(theme)
                                onDismiss()
                            }),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedTheme == theme,
                            onClick = {
                                onSelectTheme(theme)
                                onDismiss()
                            }
                        )

                        Text(
                            text = when(theme){
                                ThemeType.SYSTEM -> LocalizedContext.current.getString(R.string.theme_text_1)
                                ThemeType.LIGHT -> LocalizedContext.current.getString(R.string.theme_text_2)
                                ThemeType.DARK -> LocalizedContext.current.getString(R.string.theme_text_3)
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = LocalizedContext.current.getString(R.string.close)
                )
            }
        }
    )
}
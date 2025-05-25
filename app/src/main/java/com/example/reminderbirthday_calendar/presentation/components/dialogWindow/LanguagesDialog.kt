package com.example.reminderbirthday_calendar.presentation.components.dialogWindow

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.models.settings.LanguageType
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R

@Composable
fun LanguagesDialog(
    onDismiss: () -> Unit,
    selectedLanguage: LanguageType,
    onSelectLanguage: (LanguageType) -> Unit
) {
    val flagMap = mapOf(
        LanguageType.ENGLISH to R.drawable.flag_of_the_united_kingdom,
        LanguageType.RUSSIAN to R.drawable.flag_of_russia,
        LanguageType.BELARUSIAN to R.drawable.flag_of_belarus
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = LocalizedContext.current.getString(R.string.language_title_dialog)) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                LanguageType.entries.forEach { language ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = true, onClick = {
                                onSelectLanguage(language)
                                onDismiss()
                            }),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedLanguage == language,
                            onClick = {
                                onSelectLanguage(language)
                                onDismiss()
                            }
                        )

                        flagMap[language]?.let {
                            Image(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(it),
                                contentDescription = "flag icon"
                            )

                            Spacer(modifier = Modifier.width(12.dp))
                        }

                        Text(text = when(language){
                            LanguageType.SYSTEM -> LocalizedContext.current.getString(R.string.language_text_1)
                            LanguageType.ENGLISH -> LocalizedContext.current.getString(R.string.language_text_2)
                            LanguageType.RUSSIAN -> LocalizedContext.current.getString(R.string.language_text_3)
                            LanguageType.BELARUSIAN -> LocalizedContext.current.getString(R.string.language_text_4)
                        })
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = LocalizedContext.current.getString(R.string.close))
            }
        }
    )
}
package com.example.reminderbirthday_calendar.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reminderbirthday_calendar.presentation.components.settings.SettingsItem

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
){
   val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.then(modifier).padding(12.dp).verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SettingsItem(
            icon = Icons.Outlined.NotificationsActive,
            title = "Notification",
            subtitle = "Receive birthday reminders",
            hasSwitch = true,
            isSwitchChecked = false,
            onSwitchChange = {},
            onClick = {}
        )

        SettingsItem(
            icon = Icons.Outlined.Alarm,
            title = "Time reminder",
            subtitle = "Set time notification",
            hasSwitch = false,
            isSwitchChecked = false,
            onSwitchChange = {},
            onClick = {}
        )

        SettingsItem(
            icon = Icons.Outlined.ColorLens,
            title = "Theme",
            subtitle = "Select theme app",
            hasSwitch = false,
            isSwitchChecked = false,
            onSwitchChange = {},
            onClick = {}
        )

        SettingsItem(
            icon = Icons.Outlined.Language,
            title = "Language",
            subtitle = "Select language app",
            hasSwitch = false,
            isSwitchChecked = false,
            onSwitchChange = {},
            onClick = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SettingsScreenPreview(){
    SettingsScreen()
}
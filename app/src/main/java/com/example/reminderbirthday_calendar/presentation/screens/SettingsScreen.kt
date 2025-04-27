package com.example.reminderbirthday_calendar.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.CrueltyFree
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.SaveAs
import androidx.compose.material.icons.outlined.TableView
import androidx.compose.material.icons.outlined.UploadFile
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reminderbirthday_calendar.presentation.components.settings.SettingsItem
import com.example.reminderbirthday_calendar.presentation.event.ImportExportEvent
import com.example.reminderbirthday_calendar.presentation.sharedFlow.ImportExportSharedFlow
import com.example.reminderbirthday_calendar.presentation.viewModel.ImportExportViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    importExportViewModel: ImportExportViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val listState = rememberLazyListState()
    var lazyKey = 0

    val importExportEventToast = importExportViewModel.eventToast

    LaunchedEffect(Unit) {
        importExportEventToast.collect { sharedEvent ->
            when(sharedEvent){
                is ImportExportSharedFlow.ShowToast -> {
                    Toast.makeText(context, sharedEvent.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.then(modifier).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        stickyHeader(key = lazyKey++){
            Text(
                text = "Preferences",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 4.dp, bottom = 8.dp)
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.NotificationsActive,
                title = "Notification",
                subtitle = "Receive birthday reminders",
                hasSwitch = true,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {}
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.Alarm,
                title = "Time reminder",
                subtitle = "Set time notification",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {}
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.ColorLens,
                title = "Theme",
                subtitle = "Select theme app",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {}
            )
        }

        item(key = lazyKey++) {
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

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.AutoAwesome,
                title = "Western zodiac",
                subtitle = "Enable zodiac",
                hasSwitch = true,
                isSwitchChecked = true,
                onSwitchChange = {},
                onClick = {}
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.CrueltyFree,
                title = "Chinese zodiac",
                subtitle = "Enable zodiac",
                hasSwitch = true,
                isSwitchChecked = true,
                onSwitchChange = {},
                onClick = {}
            )
        }

        stickyHeader(key = lazyKey++) {
            Text(
                text = "Export and import",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 4.dp, bottom = 8.dp, top = 8.dp)
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.SaveAs,
                title = "Reimport events",
                subtitle = "Import events from this phone",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {}
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.UploadFile,
                title = "Export events (JSON)",
                subtitle = "Export events to the external dir",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    importExportViewModel.onEvent(event = ImportExportEvent.EventsToJsonToExternalDirExport)
                }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.TableView,
                title = "Export events (CSV)",
                subtitle = "Export events to the external dir",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    importExportViewModel.onEvent(event = ImportExportEvent.EventsToCsvToExternalDirExport)
                }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.CloudUpload,
                title = "Cloud import events",
                subtitle = "Import events from the firebase",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {}
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.CloudDownload,
                title = "Cloud export events",
                subtitle = "Export events to the firebase",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {}
            )
        }

        stickyHeader(key = lazyKey++) {
            Text(
                text = "Data",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 4.dp, bottom = 8.dp, top = 8.dp)
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.MarkEmailRead,
                title = "Sign-in with google",
                subtitle = "koxan@gmail.com",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {}
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.CalendarMonth,
                title = "Total events",
                subtitle = "0 events",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {}
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.Info,
                title = "About birthday reminder",
                subtitle = "Version 1.0.0",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {}
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun SettingsScreenPreview(){
    SettingsScreen()
}
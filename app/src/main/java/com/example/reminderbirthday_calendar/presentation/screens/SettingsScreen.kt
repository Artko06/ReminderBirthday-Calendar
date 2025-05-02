package com.example.reminderbirthday_calendar.presentation.screens

import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.CrueltyFree
import androidx.compose.material.icons.outlined.DriveFileMoveRtl
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.SaveAs
import androidx.compose.material.icons.outlined.UploadFile
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reminderbirthday_calendar.intents.settingsAppIntent.settingsAppDetailsIntent
import com.example.reminderbirthday_calendar.intents.settingsNotification.settingsNotificationIntent
import com.example.reminderbirthday_calendar.intents.shareIntent.shareFileIntent
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.DeleteAllEventsDialog
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.ReadContactsPermissionDialog
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.SortEventTypeDialog
import com.example.reminderbirthday_calendar.presentation.components.settings.NotificationPermissionDialog
import com.example.reminderbirthday_calendar.presentation.components.settings.RedClearButton
import com.example.reminderbirthday_calendar.presentation.components.settings.SettingsItem
import com.example.reminderbirthday_calendar.presentation.event.EventsEvent.ClearEvents
import com.example.reminderbirthday_calendar.presentation.event.EventsEvent.CloseDeleteAllEventsDialog
import com.example.reminderbirthday_calendar.presentation.event.EventsEvent.ShowDeleteAllEventsDialog
import com.example.reminderbirthday_calendar.presentation.event.EventsEvent.UpdateEvents
import com.example.reminderbirthday_calendar.presentation.event.ImportExportEvent
import com.example.reminderbirthday_calendar.presentation.event.PreferencesEvent
import com.example.reminderbirthday_calendar.presentation.sharedFlow.EventsSharedFlow
import com.example.reminderbirthday_calendar.presentation.sharedFlow.ImportExportSharedFlow
import com.example.reminderbirthday_calendar.presentation.viewModel.EventsViewModel
import com.example.reminderbirthday_calendar.presentation.viewModel.ImportExportViewModel
import com.example.reminderbirthday_calendar.presentation.viewModel.PreferencesViewModel


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    preferencesViewModel: PreferencesViewModel = hiltViewModel(),
    importExportViewModel: ImportExportViewModel = hiltViewModel(),
    eventsViewModel: EventsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val listState = rememberLazyListState()
    var lazyKey = 0

    val preferencesState = preferencesViewModel.preferencesState.collectAsState().value
    val eventsState = eventsViewModel.eventState.collectAsState().value
    val importExportState = importExportViewModel.importExportState.collectAsState().value

    val launcherPickerFile = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { selectedUri: Uri? ->
        selectedUri?.let { uri ->
            if (MimeTypeMap.getFileExtensionFromUrl(uri.toString()) == "json")
                importExportViewModel.onEvent(ImportExportEvent.ImportEventsFromJson(uri = uri))
            else if (MimeTypeMap.getFileExtensionFromUrl(uri.toString()) == "csv")
                importExportViewModel.onEvent(ImportExportEvent.ImportEventsFromCsv(uri = uri))
            else Toast.makeText(context, "Incorrect extension file", Toast.LENGTH_SHORT).show()
        }
    }

    val importExportSharedFlow = importExportViewModel.importExportSharedFlow

    LaunchedEffect(Unit) {
        importExportSharedFlow.collect { sharedFlow ->
            when (sharedFlow) {
                is ImportExportSharedFlow.ShowToast -> {
                    Toast.makeText(context, sharedFlow.message, Toast.LENGTH_SHORT).show()
                }

                is ImportExportSharedFlow.ShowShareView -> {
                    context.startActivity(
                        shareFileIntent(
                            typeFile = sharedFlow.typeShareFile,
                            context = context
                        )
                    )
                }

                is ImportExportSharedFlow.UpdateEventsAfterImport -> {
                    eventsViewModel.onEvent(event = UpdateEvents(events = sharedFlow.events))
                }
            }
        }
    }

    val eventsSharedFlow = eventsViewModel.eventsSharedFlow

    LaunchedEffect(Unit) {
        eventsSharedFlow.collect { sharedFlow ->
            when (sharedFlow) {
                is EventsSharedFlow.ShowToast -> {
                    Toast.makeText(context, sharedFlow.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if (preferencesState.isShowSettingsNotificationDialog) {
        NotificationPermissionDialog(
            onConfirmButton = {
                context.startActivity(settingsNotificationIntent(context = context))
            },
            onDismiss = {
                preferencesViewModel.onEvent(event = PreferencesEvent.CloseSettingsNotificationDialog)
            },
            statusNotification = preferencesState.isEnableStatusNotification
        )
    }

    if (eventsState.isShowAllEventsDeleteDialog) {
        DeleteAllEventsDialog(
            onConfirmButton = {
                eventsViewModel.onEvent(event = ClearEvents)
            },
            onDismiss = {
                eventsViewModel.onEvent(event = CloseDeleteAllEventsDialog)
            }
        )
    }

    if (importExportState.isShowReadContactPermDialog) {
        ReadContactsPermissionDialog(
            onConfirmButton = {
                context.startActivity(settingsAppDetailsIntent(context = context))
            },
            onDismiss = {
                importExportViewModel.onEvent(event = ImportExportEvent.CloseReadContactPermDialog)
            }
        )
    }

    if (preferencesState.isShowStatusTypeEventsDialog) {
        SortEventTypeDialog(
            onDismiss = {
                preferencesViewModel.onEvent(event = PreferencesEvent.CloseStatusTypeEventDialog)
            },
            changeStatusShowAnniversary = {
                preferencesViewModel.onEvent(event = PreferencesEvent.ChangeStatusShowAnniversaryEvent)
            },
            changeStatusShowBirthday = {
                preferencesViewModel.onEvent(event = PreferencesEvent.ChangeStatusShowBirthdayEvent)
            },
            changeStatusShowOther = {
                preferencesViewModel.onEvent(event = PreferencesEvent.ChangeStatusShowOtherEvent)
            },
            statusShowBirthday = preferencesState.isEnableShowBirthdayEvent,
            statusShowAnniversary = preferencesState.isEnableShowAnniversaryEvent,
            statusShowOther = preferencesState.isEnableShowOtherEvent
        )
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .then(modifier)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        stickyHeader(key = lazyKey++) {
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
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    preferencesViewModel.onEvent(event = PreferencesEvent.ShowSettingsNotificationDialog)
                },
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
                icon = Icons.Outlined.Cake,
                title = "Type event",
                subtitle = "Select types of events",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = { preferencesViewModel.onEvent(event = PreferencesEvent.ShowStatusTypeEventDialog) }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.AutoAwesome,
                title = "Western zodiac",
                subtitle = "Enable zodiac",
                hasSwitch = true,
                isSwitchChecked = preferencesState.isEnableWesternZodiac,
                onSwitchChange = {
                    preferencesViewModel.onEvent(event = PreferencesEvent.ChangeWesternZodiacStatus)
                },
                onClick = {
                    preferencesViewModel.onEvent(event = PreferencesEvent.ChangeWesternZodiacStatus)
                }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.CrueltyFree,
                title = "Chinese zodiac",
                subtitle = "Enable zodiac",
                hasSwitch = true,
                isSwitchChecked = preferencesState.isEnableChineseZodiac,
                onSwitchChange = {
                    preferencesViewModel.onEvent(event = PreferencesEvent.ChangeChineseZodiacStatus)
                },
                onClick = {
                    preferencesViewModel.onEvent(event = PreferencesEvent.ChangeChineseZodiacStatus)
                }
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
                subtitle = "Import events from contacts app",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = { importExportViewModel.onEvent(event = ImportExportEvent.ImportEventsFromContacts) }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.UploadFile,
                title = "Export events and share (JSON)",
                subtitle = "Share events with other apps",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    importExportViewModel.onEvent(event = ImportExportEvent.ExportEventsToJson)
                }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.UploadFile,
                title = "Export events and share (CSV)",
                subtitle = "Share events with other apps",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    importExportViewModel.onEvent(event = ImportExportEvent.ExportEventsToCsv)
                }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.DriveFileMoveRtl,
                title = "Import events (JSON or CSV)",
                subtitle = "Import events from your file system",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    launcherPickerFile.launch("*/*")
                }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.CloudUpload,
                title = "Cloud export events",
                subtitle = "Export events to the firebase",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    importExportViewModel.onEvent(event = ImportExportEvent.UploadEventsToRemote)
                }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.CloudDownload,
                title = "Cloud import events",
                subtitle = "Import events from the firebase",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    importExportViewModel.onEvent(event = ImportExportEvent.GetEventsFromRemote)
                }
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
                subtitle = importExportState.googleAuthEmail ?: "Auth pls \uD83E\uDD7A",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = { importExportViewModel.onEvent(event = ImportExportEvent.GoogleSignInOrOut) }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.CalendarMonth,
                title = "Total events",
                subtitle = "${eventsState.events.size} events",
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    Toast.makeText(
                        context,
                        "You have ${eventsState.events.size} events",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
                onClick = {
                    Toast.makeText(
                        context,
                        "Version this app is 1.0.0",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        item(key = lazyKey++) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                RedClearButton(
                    onClear = {
                        eventsViewModel.onEvent(event = ShowDeleteAllEventsDialog)
                    }
                )
            }
        }


    }
}


@Preview(showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}
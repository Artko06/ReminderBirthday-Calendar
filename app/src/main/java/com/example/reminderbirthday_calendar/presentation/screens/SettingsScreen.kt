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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R
import com.example.reminderbirthday_calendar.intents.settingsAppIntent.settingsAppDetailsIntent
import com.example.reminderbirthday_calendar.intents.settingsNotification.settingsNotificationIntent
import com.example.reminderbirthday_calendar.intents.shareIntent.shareFileIntent
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.DeleteAllEventsDialog
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.LanguagesDialog
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.NotificationPermissionDialog
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.ReadContactsPermissionDialog
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.SortEventTypeDialog
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.ThemeDialog
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
import com.example.reminderbirthday_calendar.util.getAppVersion


@Composable
fun SettingsScreen(
    onNavigateToTimeReminderScreen: () -> Unit,
    modifier: Modifier = Modifier,
    preferencesViewModel: PreferencesViewModel = hiltViewModel(),
    importExportViewModel: ImportExportViewModel = hiltViewModel(),
    eventsViewModel: EventsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val localizedContext = LocalizedContext.current

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
                    val message = localizedContext
                        .getString(sharedFlow.messageResId, *sharedFlow.formatArgs.toTypedArray())

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
                    val message = localizedContext
                        .getString(sharedFlow.messageResId, *sharedFlow.formatArgs.toTypedArray())

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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

    if (preferencesState.isShowAppThemeDialog){
        ThemeDialog(
            onDismiss = {
                preferencesViewModel.onEvent(event = PreferencesEvent.CloseAppThemeDialog)
            },
            selectedTheme = preferencesState.selectedTheme,
            onSelectTheme = { theme ->
                preferencesViewModel.onEvent(event = PreferencesEvent.ChangeAppTheme(theme))
            }
        )
    }

    if (preferencesState.isShowAppLanguageDialog){
        LanguagesDialog(
            onDismiss = {
                preferencesViewModel.onEvent(event = PreferencesEvent.CloseAppLanguageDialog)
            },
            selectedLanguage = preferencesState.selectedLanguage,
            onSelectLanguage = { language ->
                preferencesViewModel.onEvent(event = PreferencesEvent.ChangeAppLanguage(language))
            },
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
                text = LocalizedContext.current.getString(R.string.preferences),
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
                title = LocalizedContext.current.getString(R.string.notification),
                subtitle = LocalizedContext.current.getString(R.string.notification_info),
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
                title = LocalizedContext.current.getString(R.string.time_notifications),
                subtitle = LocalizedContext.current.getString(R.string.time_notification_info),
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = { onNavigateToTimeReminderScreen() }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.ColorLens,
                title = LocalizedContext.current.getString(R.string.theme),
                subtitle = LocalizedContext.current.getString(R.string.theme_info),
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    preferencesViewModel.onEvent(event = PreferencesEvent.ShowAppThemeDialog)
                }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.Language,
                title = LocalizedContext.current.getString(R.string.language),
                subtitle = LocalizedContext.current.getString(R.string.language_info),
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    preferencesViewModel.onEvent(event = PreferencesEvent.ShowAppLanguageDialog)
                }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.Cake,
                title = LocalizedContext.current.getString(R.string.type_event),
                subtitle = LocalizedContext.current.getString(R.string.type_event_info),
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = { preferencesViewModel.onEvent(event = PreferencesEvent.ShowStatusTypeEventDialog) }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.AutoAwesome,
                title = LocalizedContext.current.getString(R.string.zodiac_sign),
                subtitle = LocalizedContext.current.getString(R.string.sign_info),
                hasSwitch = true,
                isSwitchChecked = preferencesState.isEnableZodiacSign,
                onSwitchChange = {
                    preferencesViewModel.onEvent(event = PreferencesEvent.ChangeZodiacSignStatus)
                },
                onClick = {
                    preferencesViewModel.onEvent(event = PreferencesEvent.ChangeZodiacSignStatus)
                }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.CrueltyFree,
                title = LocalizedContext.current.getString(R.string.chinese_sign),
                subtitle = LocalizedContext.current.getString(R.string.sign_info),
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
                text = LocalizedContext.current.getString(R.string.export_and_import),
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
                title = LocalizedContext.current.getString(R.string.import_events),
                subtitle = LocalizedContext.current.getString(R.string.import_events_info),
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = { importExportViewModel.onEvent(event = ImportExportEvent.ImportEventsFromContacts) },
                isLoadingStatus = importExportState.isLoadingReimportEvent
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.UploadFile,
                title = LocalizedContext.current.getString(R.string.export_event_json),
                subtitle = LocalizedContext.current.getString(R.string.export_event_file_info),
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    importExportViewModel.onEvent(event = ImportExportEvent.ExportEventsToJson)
                },
                isLoadingStatus = importExportState.isLoadingExportJson
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.UploadFile,
                title = LocalizedContext.current.getString(R.string.export_event_csv),
                subtitle = LocalizedContext.current.getString(R.string.export_event_file_info),
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    importExportViewModel.onEvent(event = ImportExportEvent.ExportEventsToCsv)
                },
                isLoadingStatus = importExportState.isLoadingExportCsv
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.DriveFileMoveRtl,
                title = LocalizedContext.current.getString(R.string.import_events_file),
                subtitle = LocalizedContext.current.getString(R.string.import_events_file_info),
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    launcherPickerFile.launch("*/*")
                },
                isLoadingStatus = importExportState.isLoadingImportCsv || importExportState.isLoadingImportJson
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.CloudUpload,
                title = LocalizedContext.current.getString(R.string.cloud_export),
                subtitle = LocalizedContext.current.getString(R.string.cloud_export_info),
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    importExportViewModel.onEvent(event = ImportExportEvent.UploadEventsToRemote)
                },
                isLoadingStatus = importExportState.isLoadingRemoteExport
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.CloudDownload,
                title = LocalizedContext.current.getString(R.string.cloud_import),
                subtitle = LocalizedContext.current.getString(R.string.cloud_import_info),
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    importExportViewModel.onEvent(event = ImportExportEvent.GetEventsFromRemote)
                },
                isLoadingStatus = importExportState.isLoadingRemoteImport
            )
        }

        stickyHeader(key = lazyKey++) {
            Text(
                text = LocalizedContext.current.getString(R.string.data),
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
                title = LocalizedContext.current.getString(R.string.sign_in_with_google),
                subtitle = importExportState.googleAuthEmail ?: (LocalizedContext.current.getString(
                    R.string.sign_in_with_google_info) + " \uD83E\uDD7A"),
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = { importExportViewModel.onEvent(event = ImportExportEvent.GoogleSignInOrOut) },
                isLoadingStatus = importExportState.isLoadingSignInWithGoogle
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.CalendarMonth,
                title = LocalizedContext.current.getString(R.string.total_events),
                subtitle = eventsState.events.size.let {
                    LocalizedContext.current.resources.getQuantityString(R.plurals.total_events_count, it, it)
                },
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {
                    Toast.makeText(
                        context,
                        localizedContext.getString(R.string.you_have_events, eventsState.events.size.toString()),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        item(key = lazyKey++) {
            SettingsItem(
                icon = Icons.Outlined.Info,
                title = LocalizedContext.current.getString(R.string.about_app),
                subtitle = LocalizedContext.current.getString(R.string.about_app_info, getAppVersion(context = context)),
                hasSwitch = false,
                isSwitchChecked = false,
                onSwitchChange = {},
                onClick = {}
            )
        }

        item(key = lazyKey++) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                RedClearButton(
                    text = LocalizedContext.current.getString(R.string.clear_events),
                    onClear = {
                        eventsViewModel.onEvent(event = ShowDeleteAllEventsDialog)
                    }
                )
            }
        }


    }
}
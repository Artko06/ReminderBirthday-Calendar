package com.example.reminderbirthday_calendar.presentation.screens

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImportContacts
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.models.date.MonthYear
import com.example.domain.util.extensionFunc.calculateDaysLeft
import com.example.domain.util.extensionFunc.calculateNextAge
import com.example.domain.util.extensionFunc.nextEvent
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R
import com.example.reminderbirthday_calendar.intents.settingsAppIntent.settingsAppDetailsIntent
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.ReadContactsPermissionDialog
import com.example.reminderbirthday_calendar.presentation.components.evetns.DaysLeftButton
import com.example.reminderbirthday_calendar.presentation.components.evetns.EventItem
import com.example.reminderbirthday_calendar.presentation.components.evetns.MonthYearText
import com.example.reminderbirthday_calendar.presentation.components.evetns.SearchLine
import com.example.reminderbirthday_calendar.presentation.components.evetns.SelectorSortEventType
import com.example.reminderbirthday_calendar.presentation.event.EventsEvent
import com.example.reminderbirthday_calendar.presentation.sharedFlow.EventsSharedFlow
import com.example.reminderbirthday_calendar.presentation.sharedFlow.ImportExportSharedFlow
import com.example.reminderbirthday_calendar.presentation.viewModel.EventsViewModel
import com.example.reminderbirthday_calendar.presentation.viewModel.ImportExportViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.time.format.DateTimeFormatter

@Composable
fun EventsScreen(
    modifier: Modifier = Modifier,
    onNavigateToEventDetailScreen: (Long) -> Unit,
    eventsViewModel: EventsViewModel = hiltViewModel(),
    importExportViewModel: ImportExportViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val localizedContext = LocalizedContext.current

    val eventState = eventsViewModel.eventState.collectAsState().value
    val stateLazyColumn = rememberLazyListState()

    val eventsSharedFlow = eventsViewModel.eventsSharedFlow
    val importExportSharedFlow = importExportViewModel.importExportSharedFlow

    @OptIn(ExperimentalPermissionsApi::class)
    val permissionsReadWriteContactsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
        )
    )

    LaunchedEffect(Unit) {
        @OptIn(ExperimentalPermissionsApi::class)
        permissionsReadWriteContactsState.launchMultiplePermissionRequest()
    }

    LaunchedEffect(Unit) {
        eventsSharedFlow.collect { sharedFlow ->
            when(sharedFlow){
                is EventsSharedFlow.ShowToast -> {
                    val message = localizedContext
                        .getString(sharedFlow.messageResId, *sharedFlow.formatArgs.toTypedArray())

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        importExportSharedFlow.collect { sharedFlow ->
            when(sharedFlow){
                is ImportExportSharedFlow.ShowShareView -> {}
                is ImportExportSharedFlow.ShowToast -> {
                    val message = localizedContext
                        .getString(sharedFlow.messageResId, *sharedFlow.formatArgs.toTypedArray())

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if(eventState.isShowReadContactPermDialog){
        ReadContactsPermissionDialog(
            onConfirmButton = {
                context.startActivity(settingsAppDetailsIntent(context = context))
            },
            onDismiss = {
                eventsViewModel.onEvent(event = EventsEvent.CloseReadContactPermDialog)
            }
        )
    }

    Column(
        modifier = Modifier
            .then(modifier)
            .padding(12.dp)
    ) {
        SearchLine(
            value = eventState.searchStr,
            onValueChange = { newStr ->
                eventsViewModel.onEvent(event = EventsEvent.UpdateSearchLine(newStr))
            },
            placeholder = LocalizedContext.current.getString(R.string.search_placeholder)
        )

        Spacer(modifier = Modifier.height(12.dp))

        SelectorSortEventType(
            modifier = Modifier.fillMaxWidth(),
            onClick = { sortType ->
                eventsViewModel.onEvent(event = EventsEvent.SelectSortType(sortType)) },
            selectedSortType = eventState.sortTypeEvent
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ){
            DaysLeftButton(
                text = LocalizedContext.current.getString(R.string.days_before_events),
                onClick = { eventsViewModel.onEvent(event = EventsEvent.ChangeStatusViewDaysLeft) },
                isActive = eventState.isViewDaysLeft
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (eventState.events.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (eventState.isLoadingImportEvents){
                    CircularProgressIndicator(
                        modifier = Modifier.size(100.dp)
                    )
                } else{
                    Icon(
                        imageVector = Icons.Filled.SentimentVeryDissatisfied,
                        contentDescription = "No events icon",
                        modifier = Modifier.size(100.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = LocalizedContext.current.getString(R.string.event_list_is_empty),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = LocalizedContext.current.getString(R.string.event_list_is_empty_info),
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { eventsViewModel.onEvent(event = EventsEvent.ImportEventsFromContacts) },
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ImportContacts,
                            contentDescription = "Import events",
                        )

                        Text(
                            text = LocalizedContext.current.getString(R.string.import_events),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        } else {
            val events = if (eventState.searchStr.isEmpty()) eventState.events else eventState.filterEvents

            val groupedEvents = events
                .sortedBy { it.originalDate.calculateDaysLeft() }
                .groupBy { event ->
                    val nextDate = event.originalDate.nextEvent()
                    MonthYear(nextDate.year, nextDate.monthValue)
                }

            LazyColumn(state = stateLazyColumn) {
                groupedEvents.forEach { (yearMonthPair, eventsInGroup) ->
                    val (year, month) = yearMonthPair

                    item(key = "MonthYearText_${month}_$year") {
                        MonthYearText(
                            numberMonth = month,
                            numberYear = year
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    items(
                        items = eventsInGroup,
                        key = { it.id.toString() + it.nameContact + it.surnameContact }
                    ) { event ->
                        EventItem(
                            id = event.id,
                            name = event.nameContact,
                            surname = event.surnameContact ?: "",
                            eventType = event.eventType,
                            sortTypeEvent = event.sortTypeEvent,
                            date = event.originalDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                            yearMatter = event.yearMatter,
                            age = event.originalDate.calculateNextAge(),
                            isViewDaysLeft = eventState.isViewDaysLeft,
                            daysLeft = event.originalDate.calculateDaysLeft(),
                            image = event.image,
                            onNavigateByClick = onNavigateToEventDetailScreen
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
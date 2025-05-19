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
import com.example.domain.util.extensionFunc.calculateDaysLeft
import com.example.domain.util.extensionFunc.calculateNextAge
import com.example.reminderbirthday_calendar.intents.settingsAppIntent.settingsAppDetailsIntent
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.ReadContactsPermissionDialog
import com.example.reminderbirthday_calendar.presentation.components.evetns.DaysLeftButton
import com.example.reminderbirthday_calendar.presentation.components.evetns.EventItem
import com.example.reminderbirthday_calendar.presentation.components.evetns.MonthYearText
import com.example.reminderbirthday_calendar.presentation.components.evetns.SearchLine
import com.example.reminderbirthday_calendar.presentation.components.evetns.SelectorSortEventType
import com.example.reminderbirthday_calendar.presentation.event.EventsEvent
import com.example.reminderbirthday_calendar.presentation.sharedFlow.EventsSharedFlow
import com.example.reminderbirthday_calendar.presentation.viewModel.EventsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.time.format.DateTimeFormatter

@Composable
fun EventsScreen(
    modifier: Modifier = Modifier,
    onNavigateToEventDetailScreen: (Long) -> Unit,
    eventsViewModel: EventsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val eventState = eventsViewModel.eventState.collectAsState().value
    val stateLazyColumn = rememberLazyListState()

    val eventsSharedFlow = eventsViewModel.eventsSharedFlow

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
                    Toast.makeText(context, sharedFlow.message, Toast.LENGTH_SHORT).show()
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
            }
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

                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = "Event list is empty",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Import events from contacts, file or add manually",
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { eventsViewModel.onEvent(event = EventsEvent.ImportEventsFromContacts) },
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Filled.ImportContacts,
                            contentDescription = "Import events",
                        )

                        Text(
                            text = "Import events",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        } else {
            val events = if (eventState.searchStr.isEmpty()) eventState.events else eventState.filterEvents

            val groupedEvents = events.groupBy { it.originalDate.monthValue }

            LazyColumn(state = stateLazyColumn) {
                groupedEvents.forEach { (monthNumber, eventsInMonth) ->
                    item(
                        key = "MonthYearText_$monthNumber"
                    ) {
                        MonthYearText(
                            numberMonth = monthNumber,
                            numberYear = eventsInMonth[0].originalDate.year +
                                    eventsInMonth[0].originalDate.calculateNextAge()
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    items(
                        items = eventsInMonth,
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
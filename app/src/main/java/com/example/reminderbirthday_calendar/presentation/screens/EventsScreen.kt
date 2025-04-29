package com.example.reminderbirthday_calendar.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.util.extensionFunc.calculateNextAge
import com.example.reminderbirthday_calendar.presentation.components.evetns.EventItem
import com.example.reminderbirthday_calendar.presentation.components.evetns.SearchLine
import com.example.reminderbirthday_calendar.presentation.event.EventsEvent
import com.example.reminderbirthday_calendar.presentation.sharedFlow.EventsSharedFlow
import com.example.reminderbirthday_calendar.presentation.viewModel.EventsViewModel
import java.time.format.DateTimeFormatter

@Composable
fun EventsScreen(
    modifier: Modifier = Modifier,
    eventsViewModel: EventsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val eventState = eventsViewModel.eventState.collectAsState().value
    val stateLazyColumn = rememberLazyListState()

    val eventsSharedFlow = eventsViewModel.eventsSharedFlow

    LaunchedEffect(Unit) {
        eventsSharedFlow.collect { sharedFlow ->
            when(sharedFlow){
                is EventsSharedFlow.ShowToast -> {
                    Toast.makeText(context, sharedFlow.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
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

        Spacer(modifier = Modifier.height(24.dp))

        if (eventState.events.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.SentimentVeryDissatisfied,
                    contentDescription = "No events icon",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = "Список событий пуст",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Импортируйте события или добавьте вручную",
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
            LazyColumn(
                state = stateLazyColumn
            ) {
                if (eventState.searchStr.isEmpty()) {
                    items(
                        items = eventState.events
                    ) {
                        EventItem(
                            name = it.nameContact,
                            surname = it.surnameContact ?: "",
                            eventType = it.eventType,
                            date = it.originalDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString(),
                            age = it.originalDate.calculateNextAge()
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                } else {
                    items(
                        items = eventState.filterEvents
                    ) {
                        EventItem(
                            name = it.nameContact,
                            surname = it.surnameContact ?: "",
                            eventType = it.eventType,
                            date = it.originalDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString(),
                            age = it.originalDate.calculateNextAge()
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun EventScreenPreview() {
    EventsScreen(modifier = Modifier.padding(top = 48.dp))
}
package com.example.reminderbirthday_calendar.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.reminderbirthday_calendar.presentation.viewModel.EventDetailViewModel

@Composable
fun EventDetailScreen(
    eventDetailViewModel: EventDetailViewModel,
    onBackFromDetailScreen: () -> Unit,
) {
    val eventDetailState = eventDetailViewModel.eventDetailState.collectAsState().value

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = eventDetailState.event?.nameContact.toString() +
                            eventDetailState.event?.surnameContact.toString(),
                    textAlign = TextAlign.Center
                )
            }


        }
    }
}
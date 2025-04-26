package com.example.reminderbirthday_calendar.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(){
    Scaffold { paddingValue ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValue),
            contentAlignment = Alignment.Center
        ) {
            Text("Hello from Main Screen", modifier = Modifier)
        }
    }
}
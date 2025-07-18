package com.artkotlindev.reminderbirthday_calendar.presentation.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artkotlindev.reminderbirthday_calendar.LocalizedContext
import com.artkotlindev.reminderbirthday_calendar.R
import com.artkotlindev.reminderbirthday_calendar.presentation.event.NotificationPermEvent
import com.artkotlindev.reminderbirthday_calendar.presentation.viewModel.NotificationPermViewModel

@Composable
fun NotificationPermissionScreen(
    onNavigateToMainScreen: () -> Unit,
    viewModel: NotificationPermViewModel = hiltViewModel()
) {
    val notificationPermState = viewModel.notificationState.collectAsState().value

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            onNavigateToMainScreen()
        }
    )

    LaunchedEffect(notificationPermState.requestPermission) {
        if (notificationPermState.requestPermission) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                onNavigateToMainScreen()
            }
        }
    }

    Scaffold { paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        )
        {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.NotificationsActive,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = LocalizedContext.current.getString(R.string.notification_perm_window_title),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = LocalizedContext.current.getString(R.string.notification_perm_window_text),
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }

            TextButton(
                onClick = {
                    viewModel.onEvent(event = NotificationPermEvent.OnRequestNotificationPermission)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(18.dp)
            ) {
                Text(
                    text = LocalizedContext.current.getString(R.string.allow),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
    }
}
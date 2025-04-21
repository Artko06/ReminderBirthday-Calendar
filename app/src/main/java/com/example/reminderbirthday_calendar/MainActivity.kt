package com.example.reminderbirthday_calendar

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.data.local.util.image.toBitmap
import com.example.data.repository.ContactAppRepositoryImpl
import com.example.domain.models.event.Event
import com.example.domain.repository.EventRepository
import com.example.domain.repository.ExportFileRepository
import com.example.domain.repository.GoogleClientRepository
import com.example.domain.repository.ImportFileRepository
import com.example.domain.useCase.calendar.event.ImportEventUseCase
import com.example.reminderbirthday_calendar.ui.theme.ReminderBirthday_CalendarTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var eventRepository: EventRepository
    @Inject
    lateinit var exportFileRepository: ExportFileRepository
    @Inject
    lateinit var importFileRepository: ImportFileRepository
    @Inject
    lateinit var googleClientRepository: GoogleClientRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        val zodiacCalculator: ZodiacCalculator = ZodiacCalculatorImpl(context = this)

//        // Пример получения знака зодиака по дате
//        val date = LocalDate.of(2006, 1, 3)  // Пример даты
//        val westernZodiac = zodiacCalculator.getWesternZodiac(date)
//        val chineseZodiac = zodiacCalculator.getChineseZodiac(date)

        setContent {
            ReminderBirthday_CalendarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ImportContactsScreen(
                        modifier = Modifier.padding(innerPadding),
                        eventRepository,
                        exportFileRepository,
                        importFileRepository,
                        googleClientRepository
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImportContactsScreen(
    modifier: Modifier = Modifier,
    eventRepository: EventRepository,
    exportFileRepository: ExportFileRepository,
    importFileRepository: ImportFileRepository,
    googleClientRepository: GoogleClientRepository
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val permissionState = rememberPermissionState(Manifest.permission.READ_CONTACTS)
    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
    var isSignIn by remember { mutableStateOf(googleClientRepository.isSignedIn()) }
    var isLoading by remember { mutableStateOf(false) }
    var isUploaded by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var timeGettingFileFromRemote by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            scope.launch {
                val repositoryContact = ContactAppRepositoryImpl(context.contentResolver)
                eventRepository.upsertEvents(ImportEventUseCase(repositoryContact)())
                events = eventRepository.getAllEvents().first()
                eventRepository.upsertEvents(events)
                exportFileRepository.exportEventsToJsonToExternalDir()
                exportFileRepository.exportEventsToCsvToExternalDir()
//                events = importFileRepository.importEventsFromCsv()
//                events = importFileRepository.importEventsFromJson()
            }
        }
    }

    Column(modifier = modifier.fillMaxSize().padding(12.dp)) {
        if (!permissionState.status.isGranted) {
            Text("Нет доступа к контактам", color = Color.Red)
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(events) { event ->
                    EventItem(event = event)
                }
            }

            GoogleAuthButton(
                isSignedIn = isSignIn,
                isLoading = isLoading,
                error = error,
                isUploaded = isUploaded,
                timeGettingFileFromRemote = timeGettingFileFromRemote,
                onSignInClick = {
                    scope.launch {
                        isLoading = true
                        error = null
                        isSignIn = googleClientRepository.signIn()
                        if (!isSignIn) {
                            error = "Ошибка входа. Попробуйте еще раз."
                        }
                        isLoading = false
                    }
                },
                onSignOutClick = {
                    scope.launch {
                        googleClientRepository.signOut()
                        isSignIn = false
                        error = null
                        timeGettingFileFromRemote = null
                        isUploaded = false
                    }
                },
                onUploadFile = {
                    scope.launch {
                        isUploaded = googleClientRepository.uploadEventsToRemote()
                        error = if (!isUploaded) "Ошибка отправки файла" else null
                    }
                },
                getFromRemote = {
                    scope.launch {
                        events = googleClientRepository.getEventsFromRemote()
                        timeGettingFileFromRemote = googleClientRepository.getTimeFromRemote()
                    }
                }
            )
        }
    }
}

@Composable
private fun GoogleAuthButton(
    isSignedIn: Boolean,
    isLoading: Boolean,
    error: String?,
    isUploaded: Boolean,
    timeGettingFileFromRemote : String?,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onUploadFile: () -> Unit,
    getFromRemote: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        error?.let {
            Text(it, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
        }

        if (isSignedIn) {
            OutlinedButton(
                onClick = onSignOutClick,
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
                } else {
                    Text("Sign Out")
                }
            }
            Button(onClick = {onUploadFile()}, modifier = Modifier.fillMaxWidth()) { Text("Upload file to remote") }
            Button(onClick = {getFromRemote()}, modifier = Modifier.fillMaxWidth()) { Text("Get data from remote") }
            if(timeGettingFileFromRemote != null)
                Text("File was getting with date: $timeGettingFileFromRemote")
            if (isUploaded) Text("Successfully uploaded")
        } else {
            OutlinedButton(
                onClick = onSignInClick,
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
                } else {
                    Text("Sign In With Google")
                }
            }
        }
    }
}


@Composable
fun EventItem(event: Event) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
    ) {
        if (event.image?.toBitmap()?.asImageBitmap() != null)
            Image(bitmap = event.image?.toBitmap()!!.asImageBitmap(), contentDescription = "")
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = "${event.nameContact} ${event.surnameContact} (${event.eventType.name}) ${event.originalDate}")
    }
}

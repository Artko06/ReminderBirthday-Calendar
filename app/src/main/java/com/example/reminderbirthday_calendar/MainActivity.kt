package com.example.reminderbirthday_calendar

import android.Manifest
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.data.local.util.image.toBitmap
import com.example.data.repository.ContactAppRepositoryImpl
import com.example.domain.models.event.Event
import com.example.domain.repository.EventRepository
import com.example.domain.useCase.calendar.event.ImportEventUseCase
import com.example.reminderbirthday_calendar.ui.theme.ReminderBirthday_CalendarTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var eventRepository: EventRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        val zodiacCalculator: ZodiacCalculator = ZodiacCalculatorImpl(context = this)
//
//        // Пример получения знака зодиака по дате
//        val date = LocalDate.of(2006, 1, 3)  // Пример даты
//        val westernZodiac = zodiacCalculator.getWesternZodiac(date)
//        val chineseZodiac = zodiacCalculator.getChineseZodiac(date)

        setContent {
            ReminderBirthday_CalendarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ImportContactsScreen(modifier = Modifier.padding(innerPadding), eventRepository)
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImportContactsScreen(modifier: Modifier, eventRepository: EventRepository) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val permissionState = rememberPermissionState(Manifest.permission.READ_CONTACTS)

    var events = remember { mutableStateOf<List<Event>>(emptyList()) }

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    LaunchedEffect(key1 = permissionState.status) {
        if (permissionState.status.isGranted) {
            scope.launch(Dispatchers.IO) {
                val repositoryContact = ContactAppRepositoryImpl(contentResolver = context.contentResolver)
                val useCase = ImportEventUseCase(repositoryContact)


                events.value = useCase() // обновляем список
                eventRepository.upsertEvents(events.value)

                Log.d("ComposeImport", "Импортировано ${events.value.size} контактов")
            }
        }
    }

    Column(modifier = Modifier.then(modifier).fillMaxSize().padding(12.dp)) {
        if (!permissionState.status.isGranted) {
            Text("Нет доступа к контактам", color = Color.Red)
        } else {
            LazyColumn {
                items(events.value) { event ->
                    EventItem(event = event)
                }
            }
        }
    }
}

@Composable
fun EventItem(event: Event) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 3.dp)
    ) {
        if (event.image?.toBitmap()?.asImageBitmap() != null)
            Image(bitmap = event.image?.toBitmap()!!.asImageBitmap(), contentDescription = "")
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = "${event.nameContact} ${event.surnameContact} (${event.eventType.name}) ${event.originalDate}")
    }
}


package com.example.reminderbirthday_calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.reminderbirthday_calendar.presentation.navigation.NavigationScreen
import com.example.reminderbirthday_calendar.ui.theme.ReminderBirthday_CalendarTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    @Inject
//    lateinit var eventRepository: EventRepository
//
//    @Inject
//    lateinit var exportFileRepository: ExportFileRepository
//
//    @Inject
//    lateinit var importFileRepository: ImportFileRepository
//
//    @Inject
//    lateinit var googleClientRepository: GoogleClientRepository
//
//    @Inject
//    lateinit var alarmEventScheduler: AlarmEventScheduler

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
                NavigationScreen()
            }
        }
    }
}

//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun ImportContactsScreen(
//    modifier: Modifier = Modifier,
//    eventRepository: EventRepository,
//    exportFileRepository: ExportFileRepository,
//    importFileRepository: ImportFileRepository,
//    googleClientRepository: GoogleClientRepository,
//    alarmEventScheduler: AlarmEventScheduler
//) {
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
//    val permissionReadState = rememberPermissionState(Manifest.permission.READ_CONTACTS)
//    val permissionNotificationState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
//    } else {
//        object : PermissionState {
//            @SuppressLint("InlinedApi")
//            override val permission = Manifest.permission.POST_NOTIFICATIONS
//            override val status = PermissionStatus.Granted
//            override fun launchPermissionRequest() {}
//        }
//    }
//    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
//    var isSignIn by remember { mutableStateOf(googleClientRepository.isSignedIn()) }
//    var isLoading by remember { mutableStateOf(false) }
//    var isUploaded by remember { mutableStateOf(false) }
//    var error by remember { mutableStateOf<String?>(null) }
//    var timeGettingFileFromRemote by remember { mutableStateOf<String?>(null) }
//
//    LaunchedEffect(Unit) {
//        permissionNotificationState.launchPermissionRequest()
//        permissionReadState.launchPermissionRequest()
//    }
//
//    LaunchedEffect(Unit) {
//        if (permissionNotificationState.status.isGranted) {
//            val dateTime = LocalDateTime.now().plusSeconds(1)
//            val message = "Notification: Happy birthday to Mr.XXX"
//
//            val event = AlarmEventItem(
//                dateTime = dateTime,
//                message = message
//            )
//
//            alarmEventScheduler.schedule(event)
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        if (permissionReadState.status.isGranted) {
//            scope.launch {
//                val repositoryContact = ContactAppRepositoryImpl(context.contentResolver)
//                eventRepository.upsertEvents(ImportEventUseCase(repositoryContact)())
//                events = eventRepository.getAllEvents().first()
//                eventRepository.upsertEvents(events)
//                exportFileRepository.exportEventsToJsonToExternalDir()
//                exportFileRepository.exportEventsToCsvToExternalDir()
////                events = importFileRepository.importEventsFromCsv()
////                events = importFileRepository.importEventsFromJson()
//            }
//        }
//    }
//
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(12.dp)
//    ) {
//        if (!permissionReadState.status.isGranted) {
//            Text("Нет доступа к контактам", color = Color.Red)
//        } else {
//            LazyColumn(modifier = Modifier.weight(1f)) {
//                items(events) { event ->
//                    EventItem(event = event)
//                }
//            }
//
//            GoogleAuthButton(
//                isSignedIn = isSignIn,
//                isLoading = isLoading,
//                error = error,
//                isUploaded = isUploaded,
//                timeGettingFileFromRemote = timeGettingFileFromRemote,
//                onSignInClick = {
//                    scope.launch {
//                        isLoading = true
//                        error = null
//                        isSignIn = googleClientRepository.signIn()
//                        if (!isSignIn) {
//                            error = "Ошибка входа. Попробуйте еще раз."
//                        }
//                        isLoading = false
//                    }
//                },
//                onSignOutClick = {
//                    scope.launch {
//                        googleClientRepository.signOut()
//                        isSignIn = false
//                        error = null
//                        timeGettingFileFromRemote = null
//                        isUploaded = false
//                    }
//                },
//                onUploadFile = {
//                    scope.launch {
//                        isUploaded = googleClientRepository.uploadEventsToRemote()
//                        error = if (!isUploaded) "Ошибка отправки файла" else null
//                    }
//                },
//                getFromRemote = {
//                    scope.launch {
//                        events = googleClientRepository.getEventsFromRemote()
//                        timeGettingFileFromRemote = googleClientRepository.getTimeFromRemote()
//                    }
//                }
//            )
//        }
//    }
//}
//
//@Composable
//private fun GoogleAuthButton(
//    isSignedIn: Boolean,
//    isLoading: Boolean,
//    error: String?,
//    isUploaded: Boolean,
//    timeGettingFileFromRemote: String?,
//    onSignInClick: () -> Unit,
//    onSignOutClick: () -> Unit,
//    onUploadFile: () -> Unit,
//    getFromRemote: () -> Unit
//) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        error?.let {
//            Text(it, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
//        }
//
//        if (isSignedIn) {
//            OutlinedButton(
//                onClick = onSignOutClick,
//                enabled = !isLoading
//            ) {
//                if (isLoading) {
//                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
//                } else {
//                    Text("Sign Out")
//                }
//            }
//            Button(
//                onClick = { onUploadFile() },
//                modifier = Modifier.fillMaxWidth()
//            ) { Text("Upload file to remote") }
//            Button(
//                onClick = { getFromRemote() },
//                modifier = Modifier.fillMaxWidth()
//            ) { Text("Get data from remote") }
//            if (timeGettingFileFromRemote != null)
//                Text("File was getting with date: $timeGettingFileFromRemote")
//            if (isUploaded) Text("Successfully uploaded")
//        } else {
//            OutlinedButton(
//                onClick = onSignInClick,
//                enabled = !isLoading
//            ) {
//                if (isLoading) {
//                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
//                } else {
//                    Text("Sign In With Google")
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun EventItem(event: Event) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 3.dp)
//    ) {
//        if (event.image?.toBitmap()?.asImageBitmap() != null)
//            Image(bitmap = event.image?.toBitmap()!!.asImageBitmap(), contentDescription = "")
//        Spacer(modifier = Modifier.width(12.dp))
//        Text(text = "${event.nameContact} ${event.surnameContact} (${event.eventType.name}) ${event.originalDate}")
//    }
//}

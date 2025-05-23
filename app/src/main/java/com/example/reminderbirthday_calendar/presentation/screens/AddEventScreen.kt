package com.example.reminderbirthday_calendar.presentation.screens

import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.data.local.util.image.compressImageWithResize
import com.example.data.local.util.image.toByteArray
import com.example.domain.models.settings.ThemeType
import com.example.reminderbirthday_calendar.LocalTheme
import com.example.reminderbirthday_calendar.presentation.components.addWindow.SelectorEventType
import com.example.reminderbirthday_calendar.presentation.components.addWindow.SelectorSortEventTypeForAdd
import com.example.reminderbirthday_calendar.presentation.components.addWindow.TextEntry
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.CustomDatePickerDialog
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.SelectContactDialog
import com.example.reminderbirthday_calendar.presentation.event.AddEvent
import com.example.reminderbirthday_calendar.presentation.sharedFlow.AddEventSharedFlow
import com.example.reminderbirthday_calendar.presentation.viewModel.AddEventViewModel
import com.example.reminderbirthday_calendar.ui.theme.platinum
import java.time.format.DateTimeFormatter

@Composable
fun AddEventScreen(
    onBackFromAddEventScreen: () -> Unit,
    addEventViewModel: AddEventViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val addEventState = addEventViewModel.addEventState.collectAsState().value
    val addEventSharedFlow = addEventViewModel.addEventSharedFlow

    LaunchedEffect(Unit) {
        addEventSharedFlow.collect { sharedFlow ->
            when(sharedFlow){
                is AddEventSharedFlow.ShowToast -> {
                    Toast.makeText(context, sharedFlow.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri == null) {
                addEventViewModel.onEvent(event = AddEvent.OnPickPhoto(null))
                return@rememberLauncherForActivityResult
            }

            val byteArray: ByteArray?

            try {
                val typeFile = context.contentResolver.getType(uri)
                println("MimeType file: $typeFile")

                byteArray = uri.toByteArray(context)?.compressImageWithResize(
                    format = if (typeFile?.contains("png") == true ) Bitmap.CompressFormat.PNG
                    else Bitmap.CompressFormat.JPEG
                )
            } catch (e: Exception){
                e.printStackTrace()
                return@rememberLauncherForActivityResult
            }

            addEventViewModel.onEvent(event = AddEvent.OnPickPhoto(byteArray))
        }
    )

    if (addEventState.isShowDatePicker) {
        CustomDatePickerDialog(
            initDate = addEventState.date,
            onDismiss = {
                addEventViewModel.onEvent(event = AddEvent.CloseDatePickerDialog)
            },
            changeValueDatePicker = { date ->
                addEventViewModel.onEvent(event = AddEvent.ChangeDate(date))
            }
        )
    }

    if (addEventState.isShowListContacts){
        SelectContactDialog(
            contacts = addEventState.listContacts,
            onSelectContact = { contact ->
                addEventViewModel.onEvent(event = AddEvent.OnSelectContact(contact))
            },
            onDismiss = {
                addEventViewModel.onEvent(event = AddEvent.CloseListContacts)
            }
        )
    }

    val scrollScreen = rememberScrollState()

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Filled.Cake,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollScreen),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row {
                Text(
                    text = "Adding Event",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )

                Spacer(modifier = Modifier.width(6.dp))

                Icon(
                    imageVector = Icons.Filled.Cake,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (LocalTheme.current == ThemeType.DARK) Color.LightGray else platinum)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (addEventState.pickedPhoto != null) {
                    AsyncImage(
                        model = addEventState.pickedPhoto,
                        contentDescription = "Selected image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.AddPhotoAlternate,
                        contentDescription = "Select photo",
                        tint = Color.DarkGray,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            SelectorEventType(
                onClick = { eventType ->
                    addEventViewModel.onEvent(event = AddEvent.ChangeEventType(eventType))
                },
                selectedEventType = addEventState.eventType,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )

            TextEntry(
                description = "Name",
                hint = "",
                leadingIcon = Icons.Filled.Person,
                trailingIcon = Icons.Filled.AutoStories,
                trailingIconClick = {
                    addEventViewModel.onEvent(event = AddEvent.ShowListContacts)
                },
                isLoadingTrailingIcon = addEventState.isLoadingContactList,
                textValue = addEventState.valueName,
                onValueChanged = { name ->
                    addEventViewModel.onEvent(event = AddEvent.ChangeValueName(name))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            )

            TextEntry(
                description = "Surname",
                hint = "",
                leadingIcon = Icons.Filled.Person,
                textValue = addEventState.valueSurname,
                trailingIcon = Icons.Filled.AutoStories,
                trailingIconClick = {
                    addEventViewModel.onEvent(event = AddEvent.ShowListContacts)
                },
                isLoadingTrailingIcon = addEventState.isLoadingContactList,
                onValueChanged = { surname ->
                    addEventViewModel.onEvent(event = AddEvent.ChangeValueSurname(surname))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            )

            SelectorSortEventTypeForAdd(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                onClick = { sortType ->
                    addEventViewModel.onEvent(event = AddEvent.ChangeSortType(sortType))
                },
                selectedSortType = addEventState.sortType
            )

            TextButton(
                onClick = {
                    addEventViewModel.onEvent(event = AddEvent.ShowDatePickerDialog)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .border(
                        width = 1.dp,
                        color = if (addEventState.isShowDatePicker) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (addEventState.date == null) "Select date"
                    else ("Selected: " + addEventState.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                        .toString()),
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .clickable(onClick = { addEventViewModel.onEvent(event = AddEvent.ChangeYearMatter) })
            ) {
                Icon(
                    imageVector = Icons.Filled.Quiz,
                    contentDescription = "Year is matter",
                    tint = if (addEventState.yearMatter) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Year is matter"
                )

                Checkbox(
                    checked = addEventState.yearMatter,
                    onCheckedChange = {
                        addEventViewModel.onEvent(event = AddEvent.ChangeYearMatter)
                    }
                )
            }

            TextEntry(
                description = "Notes",
                hint = "",
                leadingIcon = Icons.Filled.NoteAlt,
                textValue = addEventState.notes,
                onValueChanged = { notes ->
                    addEventViewModel.onEvent(event = AddEvent.ChangeNotes(notes))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            )

            // Кнопки
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = { onBackFromAddEventScreen() }) {
                    Text("Cancel")
                }

                TextButton(
                    onClick = {
                        addEventViewModel.onEvent(event = AddEvent.AddEventButton)
                        onBackFromAddEventScreen()
                    },
                    enabled = addEventState.isEnableAddEventButton
                ) {
                    Text("Add")
                }
            }

        }
    }
}
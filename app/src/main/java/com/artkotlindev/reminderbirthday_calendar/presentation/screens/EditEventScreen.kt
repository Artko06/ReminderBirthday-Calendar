package com.artkotlindev.reminderbirthday_calendar.presentation.screens

import android.graphics.Bitmap
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.artkotlindev.data.local.util.image.compressImageWithResize
import com.artkotlindev.data.local.util.image.toByteArray
import com.artkotlindev.reminderbirthday_calendar.LocalizedContext
import com.artkotlindev.reminderbirthday_calendar.R
import com.artkotlindev.reminderbirthday_calendar.presentation.components.addWindow.SelectorSortEventTypeForAdd
import com.artkotlindev.reminderbirthday_calendar.presentation.components.addWindow.TextEntry
import com.artkotlindev.reminderbirthday_calendar.presentation.components.dialogWindow.CustomDatePickerDialog
import com.artkotlindev.reminderbirthday_calendar.presentation.components.dialogWindow.SelectContactDialog
import com.artkotlindev.reminderbirthday_calendar.presentation.event.EditEvent
import com.artkotlindev.reminderbirthday_calendar.presentation.viewModel.EditEventViewModel
import java.time.format.DateTimeFormatter

@Composable
fun EditEventScreen(
    onBackFromEditEventScreen: () -> Unit,
    editEventViewModel: EditEventViewModel
){
    val context = LocalContext.current
    val editEventState = editEventViewModel.editEventState.collectAsState().value

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri == null) {
                editEventViewModel.onEvent(event = EditEvent.OnPickPhoto(null))
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

            editEventViewModel.onEvent(event = EditEvent.OnPickPhoto(byteArray))
        }
    )

    if (editEventState.isShowDatePicker) {
        CustomDatePickerDialog(
            initDate = editEventState.date,
            onDismiss = {
                editEventViewModel.onEvent(event = EditEvent.CloseDatePickerDialog)
            },
            changeValueDatePicker = { date ->
                editEventViewModel.onEvent(event = EditEvent.ChangeDate(date))
            }
        )
    }

    if (editEventState.isShowListContacts){
        SelectContactDialog(
            contacts = editEventState.listContacts,
            onSelectContact = { contact ->
                editEventViewModel.onEvent(event = EditEvent.OnSelectContact(contact))
            },
            onDismiss = {
                editEventViewModel.onEvent(event = EditEvent.CloseListContacts)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = LocalizedContext.current.getString(R.string.editing_event),
                    fontSize = 22.sp,
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
                    .background(Color.LightGray)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (editEventState.pickedPhoto != null) {
                    AsyncImage(
                        model = editEventState.pickedPhoto,
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

            Spacer(modifier = Modifier.width(40.dp))

            TextEntry(
                description = LocalizedContext.current.getString(R.string.name),
                hint = "",
                leadingIcon = Icons.Filled.Person,
                trailingIcon = Icons.Filled.AutoStories,
                trailingIconClick = {
                    editEventViewModel.onEvent(event = EditEvent.ShowListContacts)
                },
                isLoadingTrailingIcon = editEventState.isLoadingContactList,
                textValue = editEventState.name,
                onValueChanged = { name ->
                    if (name.length <= 20){
                        editEventViewModel.onEvent(event = EditEvent.ChangeValueName(name))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            )

            TextEntry(
                description = LocalizedContext.current.getString(R.string.surname),
                hint = "",
                leadingIcon = Icons.Filled.Person,
                textValue = editEventState.surname ?: "",
                trailingIcon = Icons.Filled.AutoStories,
                trailingIconClick = {
                    editEventViewModel.onEvent(event = EditEvent.ShowListContacts)
                },
                isLoadingTrailingIcon = editEventState.isLoadingContactList,
                onValueChanged = { surname ->
                    if (surname.length <= 20) {
                        editEventViewModel.onEvent(event = EditEvent.ChangeValueSurname(surname))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            SelectorSortEventTypeForAdd(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                onClick = { sortType ->
                    editEventViewModel.onEvent(event = EditEvent.ChangeSortType(sortType))
                },
                selectedSortType = editEventState.sortType
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = {
                    editEventViewModel.onEvent(event = EditEvent.ShowDatePickerDialog)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .border(
                        width = 1.dp,
                        color = if (editEventState.isShowDatePicker) MaterialTheme.colorScheme.primary
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
                    text = (LocalizedContext.current.getString(R.string.selected) + ": " +
                            editEventState.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString()),
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .clickable(onClick = { editEventViewModel.onEvent(event = EditEvent.ChangeYearMatter) })
            ) {
                Icon(
                    imageVector = Icons.Filled.Quiz,
                    contentDescription = "Consider the year",
                    tint = if (editEventState.yearMatter) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = LocalizedContext.current.getString(R.string.consider_the_year)
                )

                Checkbox(
                    checked = editEventState.yearMatter,
                    onCheckedChange = {
                        editEventViewModel.onEvent(event = EditEvent.ChangeYearMatter)
                    }
                )
            }

            // Кнопки
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = { onBackFromEditEventScreen() }) {
                    Text(
                        text = LocalizedContext.current.getString(R.string.cancel)
                    )
                }

                TextButton(
                    onClick = {
                        editEventViewModel.onEvent(event = EditEvent.SaveEventButton)
                        onBackFromEditEventScreen()
                    },
                    enabled = editEventState.isSaveButtonEnable
                ) {
                    Text(
                        text = LocalizedContext.current.getString(R.string.save)
                    )
                }
            }

        }
    }
}
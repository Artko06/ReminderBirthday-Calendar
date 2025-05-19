package com.example.reminderbirthday_calendar.presentation.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.ModeEditOutline
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.event.EventType
import com.example.domain.models.event.SortTypeEvent
import com.example.domain.models.settings.ThemeType
import com.example.domain.models.zodiac.ChineseZodiac
import com.example.domain.models.zodiac.WesternZodiac
import com.example.domain.util.extensionFunc.calculateDaysLeft
import com.example.domain.util.extensionFunc.calculateNextAge
import com.example.reminderbirthday_calendar.LocalTheme
import com.example.reminderbirthday_calendar.R
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.DeleteEventDialog
import com.example.reminderbirthday_calendar.presentation.components.dialogWindow.NotesDialog
import com.example.reminderbirthday_calendar.presentation.detailWindow.DetailItem
import com.example.reminderbirthday_calendar.presentation.event.DetailInfoEvent
import com.example.reminderbirthday_calendar.presentation.viewModel.EventDetailViewModel
import com.example.reminderbirthday_calendar.ui.theme.blueAzure
import com.example.reminderbirthday_calendar.ui.theme.darkGreen
import com.example.reminderbirthday_calendar.ui.theme.darkPurple
import com.example.reminderbirthday_calendar.ui.theme.darkRed
import com.example.reminderbirthday_calendar.ui.theme.yellow

@Composable
fun EventDetailScreen(
    eventDetailViewModel: EventDetailViewModel,
    onBackFromDetailScreen: () -> Unit,
    onNavigateToEditEventScreen: (Long) -> Unit
) {
    val eventDetailState = eventDetailViewModel.eventDetailState.collectAsState().value

    val rememberScroll = rememberScrollState()

    val colorSort = when (eventDetailState.event.sortTypeEvent) {
        SortTypeEvent.FAMILY -> darkRed
        SortTypeEvent.RELATIVE -> yellow
        SortTypeEvent.FRIEND -> blueAzure
        SortTypeEvent.COLLEAGUE -> darkGreen
        SortTypeEvent.OTHER -> darkPurple
    }

    if (eventDetailState.isShowDeleteDialog) {
        DeleteEventDialog(
            onConfirmButton = {
                eventDetailViewModel.onEvent(event = DetailInfoEvent.DeleteEvent)
                onBackFromDetailScreen()
            },
            onDismiss = {
                eventDetailViewModel.onEvent(event = DetailInfoEvent.OnCloseDeleteDialog)
            }
        )
    }

    if (eventDetailState.isShowNotesDialog) {
        NotesDialog(
            text = eventDetailState.newNotes,
            onChangeNotes = { newNotes ->
                eventDetailViewModel.onEvent(event = DetailInfoEvent.OnChangeNotes(newNotes))
            },
            onDismiss = {
                eventDetailViewModel.onEvent(event = DetailInfoEvent.OnCloseNotesDialog)
            },
            onConfirm = {
                eventDetailViewModel.onEvent(event = DetailInfoEvent.SaveNewNotes)
            }
        )
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScroll),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    IconButton(
                        onClick = { onBackFromDetailScreen() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "onBackFromDetailScreen"
                        )
                    }
                }

                IconButton(
                    onClick = {
                        onNavigateToEditEventScreen(eventDetailState.event.id)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ModeEditOutline,
                        contentDescription = "EditEvent"
                    )
                }

                IconButton(
                    onClick = {
                        eventDetailViewModel.onEvent(event = DetailInfoEvent.OnShowDeleteDialog)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DeleteForever,
                        contentDescription = "DeleteEvent"
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (eventDetailState.event.image != null) {
                    Image(
                        bitmap = eventDetailState.event.image!!.decodeToImageBitmap(),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .border(
                                width = 3.dp,
                                color = colorSort,
                                shape = CircleShape
                            )
                            .border(
                                width = 10.dp,
                                color = MaterialTheme.colorScheme.background,
                                shape = CircleShape
                            )
                            .clip(shape = CircleShape)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Icon photo",
                        modifier = Modifier
                            .size(150.dp)
                            .border(
                                width = 3.dp,
                                color = colorSort,
                                shape = CircleShape
                            ),
                        tint = if (LocalTheme.current == ThemeType.DARK) Color.White else Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = eventDetailState.event.let {
                    it.nameContact +
                            if (it.surnameContact != null) " ${it.surnameContact}" else ""
                },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                if (eventDetailState.event.yearMatter) {
                    Text(
                        text = eventDetailState.event.originalDate.let {
                            buildAnnotatedString {
                                append("turns ")
                                withStyle(
                                    style = SpanStyle(
                                        fontStyle = FontStyle.Italic,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(it.calculateNextAge().toString())
                                }
                                append(" years ")
                                if (it.calculateDaysLeft() != 0)
                                    append("in ")
                                withStyle(
                                    style = SpanStyle(
                                        fontStyle = FontStyle.Italic,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Normal
                                    )
                                ) {
                                    if (it.calculateDaysLeft() == 0)
                                        append("today")
                                    else
                                        append(it.calculateDaysLeft().toString())
                                }
                                if (it.calculateDaysLeft() != 0)
                                    append(if (it.calculateDaysLeft() == 1) " day" else " days")
                            }
                        },
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                DetailItem(
                    modifier = Modifier.fillMaxWidth(),
                    icon = Icons.Outlined.Cake,
                    colorIcon = MaterialTheme.colorScheme.primary,
                    text = eventDetailState.event.originalDate.let {
                        it.dayOfMonth.toString() + " " + it.month.name.lowercase()
                            .replaceFirstChar { it.uppercase() } +
                                if (eventDetailState.event.yearMatter) ", " + it.year.toString() else ""
                    },
                    aboutText =
                        if (eventDetailState.event.eventType != EventType.OTHER)
                            eventDetailState.event.eventType.name.lowercase()
                                .replaceFirstChar { it.uppercase() }
                        else "Event"
                )

                DetailItem(
                    modifier = Modifier.fillMaxWidth(),
                    icon = Icons.Outlined.StarRate,
                    colorIcon = MaterialTheme.colorScheme.primary,
                    text = eventDetailState.event.sortTypeEvent.name.lowercase()
                        .replaceFirstChar { it.uppercase() },
                    aboutText = "Tag"
                )

                if (eventDetailState.statusWesternZodiac &&
                    eventDetailState.event.eventType == EventType.BIRTHDAY
                ) {
                    DetailItem(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(
                            when (eventDetailState.westernZodiac) {
                                WesternZodiac.AQUARIUS -> R.drawable.icon_aquarius
                                WesternZodiac.PISCES -> R.drawable.icon_pisces
                                WesternZodiac.ARIES -> R.drawable.icon_aries
                                WesternZodiac.TAURUS -> R.drawable.icon_taurus
                                WesternZodiac.GEMINI -> R.drawable.icon_gemini
                                WesternZodiac.CANCER -> R.drawable.icon_cancer
                                WesternZodiac.LEO -> R.drawable.icon_leo
                                WesternZodiac.VIRGO -> R.drawable.icon_virgo
                                WesternZodiac.LIBRA -> R.drawable.icon_libra
                                WesternZodiac.SCORPIO -> R.drawable.icon_scorpio
                                WesternZodiac.SAGITTARIUS -> R.drawable.icon_sagittarius
                                WesternZodiac.CAPRICORN -> R.drawable.icon_capricorn
                            }
                        ),
                        colorIcon = MaterialTheme.colorScheme.primary,
                        text = eventDetailState.westernZodiac.name.lowercase()
                            .replaceFirstChar { it.uppercase() },
                        aboutText = "Western zodiac"
                    )
                }

                if (eventDetailState.event.yearMatter &&
                    eventDetailState.statusChineseZodiac &&
                    eventDetailState.event.eventType == EventType.BIRTHDAY
                ) {
                    DetailItem(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(
                            when (eventDetailState.chineseZodiac) {
                                ChineseZodiac.MONKEY -> R.drawable.icon_monkey
                                ChineseZodiac.ROOSTER -> R.drawable.icon_rooster
                                ChineseZodiac.DOG -> R.drawable.icon_dog
                                ChineseZodiac.PIG -> R.drawable.icon_pig
                                ChineseZodiac.RAT -> R.drawable.icon_rat
                                ChineseZodiac.OX -> R.drawable.icon_ox
                                ChineseZodiac.TIGER -> R.drawable.icon_tiger
                                ChineseZodiac.RABBIT -> R.drawable.icon_rabbit
                                ChineseZodiac.DRAGON -> R.drawable.icon_dragon
                                ChineseZodiac.SNAKE -> R.drawable.icon_snake
                                ChineseZodiac.HORSE -> R.drawable.icon_horse
                                ChineseZodiac.GOAT -> R.drawable.icon_goat
                            }
                        ),
                        colorIcon = MaterialTheme.colorScheme.primary,
                        text = eventDetailState.chineseZodiac.name.lowercase()
                            .replaceFirstChar { it.uppercase() },
                        aboutText = "Chinese zodiac"
                    )
                }

                DetailItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(16.dp))
                        .clickable(onClick = {
                            eventDetailViewModel.onEvent(event = DetailInfoEvent.OnShowNotesDialog)
                        }),
                    icon = Icons.Outlined.NoteAlt,
                    colorIcon = MaterialTheme.colorScheme.primary,
                    text = if (eventDetailState.event.notes.isNullOrBlank()) "Click to show"
                    else eventDetailState.event.notes!!.let {
                        if (it.length > 15) "${it.take(15)}..." else it
                    },
                    aboutText = "Notes"
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

        }
    }
}
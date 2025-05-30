package com.example.reminderbirthday_calendar.presentation.screens

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.models.settings.ThemeType
import com.example.domain.util.extensionFunc.calculateAgeInYear
import com.example.domain.util.extensionFunc.calculateDaysLeftWithMyYear
import com.example.reminderbirthday_calendar.LocalTheme
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R
import com.example.reminderbirthday_calendar.presentation.components.calendar.CalendarGrid
import com.example.reminderbirthday_calendar.presentation.components.calendar.MonthYearText
import com.example.reminderbirthday_calendar.presentation.components.calendar.RowDaysOfWeek
import com.example.reminderbirthday_calendar.presentation.components.evetns.EventItem
import com.example.reminderbirthday_calendar.presentation.event.CalendarEvent
import com.example.reminderbirthday_calendar.presentation.sharedFlow.ImportExportSharedFlow
import com.example.reminderbirthday_calendar.presentation.viewModel.CalendarViewModel
import com.example.reminderbirthday_calendar.presentation.viewModel.EventsViewModel
import com.example.reminderbirthday_calendar.presentation.viewModel.ImportExportViewModel
import com.example.reminderbirthday_calendar.ui.theme.platinum
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    onNavigateToEventDetailScreen: (Long) -> Unit,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    eventsViewModel: EventsViewModel = hiltViewModel(),
    importExportViewModel: ImportExportViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val localizedContext = LocalizedContext.current

    val calendarState = calendarViewModel.calendarState.collectAsState().value
    val eventState = eventsViewModel.eventState.collectAsState().value

    val pagerState = rememberPagerState(
        initialPage = calendarState.calendarPage,
        pageCount = { 12 * 5 }
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        calendarViewModel.onEvent(event = CalendarEvent.SelectDate(null))
        calendarViewModel.onEvent(event = CalendarEvent.ChangeCalendarPage(pagerState.currentPage))
    }

    val importExportSharedFlow = importExportViewModel.importExportSharedFlow

    LaunchedEffect(Unit) {
        importExportSharedFlow.collect { sharedFlow ->
            when(sharedFlow){
                is ImportExportSharedFlow.ShowShareView -> {}
                is ImportExportSharedFlow.ShowToast -> {
                    val message = localizedContext
                        .getString(sharedFlow.messageResId, *sharedFlow.formatArgs.toTypedArray())

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(
                    color = if (LocalTheme.current == ThemeType.DARK) Color.DarkGray else platinum,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(15.dp)
        ) {
            Row {
                MonthYearText(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = CircleShape
                        )
                        .padding(8.dp),
                    contentAlignment = Alignment.CenterStart,
                    monthYearMonth = calendarState.currentMonth.plusMonths(pagerState.currentPage.toLong())
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page = 0)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "to 1 page",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            RowDaysOfWeek()

            Spacer(modifier = Modifier.height(3.dp))

            HorizontalPager(
                state = pagerState,
                key = { "PageMonthYear_$it" },
                modifier = Modifier.animateContentSize(),
            ) { page ->
                val monthToShow = calendarState.currentMonth.plusMonths(page.toLong())

                CalendarGrid(
                    events = calendarState.events,
                    currentMonth = monthToShow,
                    selectedDate = calendarState.selectDate,
                    onDateSelected = { date ->
                        calendarViewModel.onEvent(event = CalendarEvent.SelectDate(date))
                    }
                )
            }

        }

        Text(
            text = LocalizedContext.current.getString(R.string.events_on) + " " +
                calendarState.selectDate.let {
                    if (it == null) {
                        calendarState.currentMonth.plusMonths(pagerState.currentPage.toLong()).let {
                            LocalizedContext.current.resources.getStringArray(R.array.months)[it.month.value - 1] +
                            ", " + it.year
                        }
                    } else {
                        val monthsArray = LocalizedContext.current.resources.getStringArray(R.array.months_with_num)
                        val text = String.format(monthsArray[it.month.value - 1], it.dayOfMonth)
                        
                        text + ", " + it.year
                    }
                },
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            items(
                items = calendarState.eventsInDate,
                key = { it.id.toString() + it.nameContact + it.surnameContact }
            ) { event ->
                val daysLeft = event.originalDate.calculateDaysLeftWithMyYear(
                    year = calendarState.currentMonth.plusMonths(pagerState.currentPage.toLong()).year
                )

                if (calendarState.selectDate == null && daysLeft < 0) return@items
                EventItem(
                    id = event.id,
                    name = event.nameContact,
                    surname = event.surnameContact ?: "",
                    eventType = event.eventType,
                    sortTypeEvent = event.sortTypeEvent,
                    date = event.originalDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    yearMatter = event.yearMatter,
                    age = event.originalDate.calculateAgeInYear(
                        targetYear = calendarState.currentMonth.plusMonths(pagerState.currentPage.toLong()).year
                    ),
                    isViewDaysLeft = eventState.isViewDaysLeft,
                    daysLeft = daysLeft,
                    image = event.image,
                    onNavigateByClick = onNavigateToEventDetailScreen
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }


}
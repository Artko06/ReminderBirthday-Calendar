package com.example.reminderbirthday_calendar.presentation.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.models.settings.ThemeType
import com.example.reminderbirthday_calendar.LocalTheme
import com.example.reminderbirthday_calendar.presentation.calendar.CalendarGrid
import com.example.reminderbirthday_calendar.presentation.calendar.MonthYearText
import com.example.reminderbirthday_calendar.presentation.calendar.RowDaysOfWeek
import com.example.reminderbirthday_calendar.presentation.event.CalendarEvent
import com.example.reminderbirthday_calendar.presentation.viewModel.CalendarViewModel
import com.example.reminderbirthday_calendar.presentation.viewModel.EventsViewModel
import com.example.reminderbirthday_calendar.ui.theme.platinum
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    eventsViewModel: EventsViewModel = hiltViewModel()
) {
    val calendarState = calendarViewModel.calendarState.collectAsState().value
    val eventState = eventsViewModel.eventState.collectAsState().value

    val pagerState = rememberPagerState(pageCount = { 12 * 5 })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != 0){
            calendarViewModel.onEvent(event = CalendarEvent.SelectDate(null))
        }
    }

    Column(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .padding(15.dp)
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
                currentMonth = monthToShow,
                selectedDate = calendarState.selectDate,
                onDateSelected = { date ->
                    calendarViewModel.onEvent(event = CalendarEvent.SelectDate(date))
                }
            )
        }

    }

}
package com.artkotlindev.reminderbirthday_calendar.presentation.components.evetns

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artkotlindev.domain.models.event.SortTypeEvent
import com.artkotlindev.reminderbirthday_calendar.LocalizedContext
import com.artkotlindev.reminderbirthday_calendar.R
import com.artkotlindev.reminderbirthday_calendar.ui.theme.blueAzure
import com.artkotlindev.reminderbirthday_calendar.ui.theme.darkGreen
import com.artkotlindev.reminderbirthday_calendar.ui.theme.darkPurple
import com.artkotlindev.reminderbirthday_calendar.ui.theme.darkRed
import com.artkotlindev.reminderbirthday_calendar.ui.theme.yellow

@Composable
fun EmptyEmoji(
    isLoadingImportEvents: Boolean,
    sortTypeEvent: SortTypeEvent?
) {
    Text(
        text = when(sortTypeEvent){
            SortTypeEvent.FAMILY -> LocalizedContext.current.getString(R.string.sort_type_event_family)
            SortTypeEvent.RELATIVE ->LocalizedContext.current.getString(R.string.sort_type_event_relative)
            SortTypeEvent.FRIEND -> LocalizedContext.current.getString(R.string.sort_type_event_friend)
            SortTypeEvent.COLLEAGUE -> LocalizedContext.current.getString(R.string.sort_type_event_colleague)
            SortTypeEvent.OTHER -> LocalizedContext.current.getString(R.string.sort_type_event_other)
            null -> LocalizedContext.current.getString(R.string.sort_type_event_all)
        },
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        color = when(sortTypeEvent) {
            SortTypeEvent.FAMILY -> darkRed
            SortTypeEvent.RELATIVE -> yellow
            SortTypeEvent.FRIEND -> blueAzure
            SortTypeEvent.COLLEAGUE -> darkGreen
            SortTypeEvent.OTHER -> darkPurple
            null -> MaterialTheme.colorScheme.onBackground
        }
    )

    if (isLoadingImportEvents){
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp)
        )
    } else{
        Icon(
            imageVector = Icons.Filled.SentimentVeryDissatisfied,
            contentDescription = "No events icon",
            modifier = Modifier.size(100.dp),
            tint = when(sortTypeEvent) {
                SortTypeEvent.FAMILY -> darkRed
                SortTypeEvent.RELATIVE -> yellow
                SortTypeEvent.FRIEND -> blueAzure
                SortTypeEvent.COLLEAGUE -> darkGreen
                SortTypeEvent.OTHER -> darkPurple
                null -> MaterialTheme.colorScheme.onBackground
            }
        )
    }
}
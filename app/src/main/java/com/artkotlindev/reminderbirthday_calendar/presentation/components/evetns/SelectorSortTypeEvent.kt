package com.artkotlindev.reminderbirthday_calendar.presentation.components.evetns

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
fun SelectorSortEventType(
    modifier: Modifier = Modifier,
    onClick: (SortTypeEvent?) -> Unit,
    selectedSortType: SortTypeEvent?
) {
    LazyRow(
        modifier = Modifier.then(modifier)
    ) {
        item {
            SortTypeButton(
                text = LocalizedContext.current.getString(R.string.sort_type_event_all),
                isSelected = selectedSortType == null,
                sortTypeEvent = null,
                onClick = { onClick(null) }
            )

            Spacer(modifier = Modifier.width(6.dp))
        }

        items(
            items = SortTypeEvent.entries.toList(),
            key = { it.name }
        ) { sortType ->
            SortTypeButton(
                text = when(sortType){
                    SortTypeEvent.FAMILY -> LocalizedContext.current.getString(R.string.sort_type_event_family)
                    SortTypeEvent.RELATIVE ->LocalizedContext.current.getString(R.string.sort_type_event_relative)
                    SortTypeEvent.FRIEND -> LocalizedContext.current.getString(R.string.sort_type_event_friend)
                    SortTypeEvent.COLLEAGUE -> LocalizedContext.current.getString(R.string.sort_type_event_colleague)
                    SortTypeEvent.OTHER -> LocalizedContext.current.getString(R.string.sort_type_event_other)
                },
                isSelected = selectedSortType == sortType,
                sortTypeEvent = sortType,
                onClick = { onClick(sortType) }
            )

            Spacer(modifier = Modifier.width(6.dp))
        }

    }
}

@Composable
fun SortTypeButton(
    text: String,
    isSelected: Boolean,
    sortTypeEvent: SortTypeEvent?,
    onClick: (SortTypeEvent?) -> Unit
){
    OutlinedButton(
        onClick = { onClick(sortTypeEvent) },
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onBackground
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.StarRate,
                contentDescription = null,
                tint = when(sortTypeEvent){
                    SortTypeEvent.FAMILY -> darkRed
                    SortTypeEvent.RELATIVE -> yellow
                    SortTypeEvent.FRIEND -> blueAzure
                    SortTypeEvent.COLLEAGUE -> darkGreen
                    SortTypeEvent.OTHER -> darkPurple
                    null -> {
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onBackground
                    }
                }
            )

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun SelectorSortEventTypePreview(){
    SelectorSortEventType(
        onClick = {},
        selectedSortType = null
    )
}
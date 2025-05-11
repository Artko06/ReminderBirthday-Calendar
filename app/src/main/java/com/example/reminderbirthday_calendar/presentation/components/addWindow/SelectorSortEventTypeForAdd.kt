package com.example.reminderbirthday_calendar.presentation.components.addWindow

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.event.SortTypeEvent
import com.example.reminderbirthday_calendar.ui.theme.backgroundBlueAzure
import com.example.reminderbirthday_calendar.ui.theme.backgroundDarkGreen
import com.example.reminderbirthday_calendar.ui.theme.backgroundDarkPurple
import com.example.reminderbirthday_calendar.ui.theme.backgroundDarkRed
import com.example.reminderbirthday_calendar.ui.theme.backgroundYellow
import com.example.reminderbirthday_calendar.ui.theme.blueAzure
import com.example.reminderbirthday_calendar.ui.theme.darkGreen
import com.example.reminderbirthday_calendar.ui.theme.darkPurple
import com.example.reminderbirthday_calendar.ui.theme.darkRed
import com.example.reminderbirthday_calendar.ui.theme.yellow

@Composable
fun SelectorSortEventTypeForAdd(
    modifier: Modifier = Modifier,
    onClick: (SortTypeEvent) -> Unit,
    selectedSortType: SortTypeEvent
) {
    LazyRow(
        modifier = Modifier.then(modifier)
    ) {
        items(
            items = SortTypeEvent.entries.toList(),
            key = { it.name }
        ) { sortType ->
            SortTypeButton(
                text = sortType.name.lowercase().replaceFirstChar { it.uppercase() },
                isSelected = selectedSortType == sortType,
                sortTypeEvent = sortType,
                onClick = { onClick(sortType) }
            )

            Spacer(modifier = Modifier.width(4.dp))
        }

    }
}

@Composable
fun SortTypeButton(
    text: String,
    isSelected: Boolean,
    sortTypeEvent: SortTypeEvent,
    onClick: (SortTypeEvent) -> Unit
){
    Button(
        onClick = { onClick(sortTypeEvent) },
        border = BorderStroke(
            1.5.dp, if (isSelected) {
                when(sortTypeEvent){
                    SortTypeEvent.FAMILY -> darkRed
                    SortTypeEvent.RELATIVE -> yellow
                    SortTypeEvent.FRIEND -> blueAzure
                    SortTypeEvent.COLLEAGUE -> darkGreen
                    SortTypeEvent.OTHER -> darkPurple
                }
            }
            else {
                when(sortTypeEvent){
                    SortTypeEvent.FAMILY -> backgroundDarkRed
                    SortTypeEvent.RELATIVE -> backgroundYellow
                    SortTypeEvent.FRIEND -> backgroundBlueAzure
                    SortTypeEvent.COLLEAGUE -> backgroundDarkGreen
                    SortTypeEvent.OTHER -> backgroundDarkPurple
                }
            }
        ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when(sortTypeEvent){
                SortTypeEvent.FAMILY -> backgroundDarkRed
                SortTypeEvent.RELATIVE -> backgroundYellow
                SortTypeEvent.FRIEND -> backgroundBlueAzure
                SortTypeEvent.COLLEAGUE -> backgroundDarkGreen
                SortTypeEvent.OTHER -> backgroundDarkPurple
            },
            contentColor = when(sortTypeEvent){
                SortTypeEvent.FAMILY -> darkRed
                SortTypeEvent.RELATIVE -> yellow
                SortTypeEvent.FRIEND -> blueAzure
                SortTypeEvent.COLLEAGUE -> darkGreen
                SortTypeEvent.OTHER -> darkPurple
            }
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.StarRate,
                contentDescription = null
            )

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun SelectorSortEventTypePreview(){
    SelectorSortEventTypeForAdd(
        onClick = {},
        selectedSortType = SortTypeEvent.RELATIVE
    )
}
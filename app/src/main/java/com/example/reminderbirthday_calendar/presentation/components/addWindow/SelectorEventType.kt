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
import androidx.compose.material.icons.outlined.Celebration
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
import com.example.domain.models.event.EventType

@Composable
fun SelectorEventType(
    modifier: Modifier = Modifier,
    onClick: (EventType) -> Unit,
    selectedEventType: EventType
) {
    LazyRow(
        modifier = Modifier.then(modifier)
    ) {
        items(
            items = EventType.entries.toList(),
            key = { it.name }
        ) {
            OutlinedButton(
                onClick = { onClick(it) },
                border = BorderStroke(
                    0.5.dp, if (selectedEventType == it) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onBackground
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Celebration,
                        contentDescription = null,
                        tint = if (selectedEventType == it) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = it.name.lowercase().replaceFirstChar { it.uppercase() },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (selectedEventType == it) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

        }
    }
}


@Composable
@Preview(showBackground = false)
fun SelectorEventTypePreview() {
    SelectorEventType(
        onClick = {},
        selectedEventType = EventType.BIRTHDAY
    )
}
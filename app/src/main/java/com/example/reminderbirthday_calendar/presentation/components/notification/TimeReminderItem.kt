package com.example.reminderbirthday_calendar.presentation.components.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrowseGallery
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.notification.NotificationEvent
import com.example.reminderbirthday_calendar.LocalizedContext
import com.example.reminderbirthday_calendar.R

@Composable
fun TimeReminderItem(
    modifier: Modifier = Modifier,
    notificationEvent: NotificationEvent,
    changeStatusNotification: (Int) -> Unit,
    selectNewDaysBefore: (NotificationEvent) -> Unit,
    timeIconClick: (NotificationEvent) -> Unit,
) {
    var isExpandedDropDown by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .then(modifier)
            .clickable(onClick = { changeStatusNotification(notificationEvent.id) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            IconButton(
                onClick = { isExpandedDropDown = !isExpandedDropDown }
            ) {
                Icon(
                    imageVector = Icons.Filled.EditCalendar,
                    contentDescription = "Day before event notification"
                )
            }

            DropdownMenu(
                expanded = isExpandedDropDown,
                onDismissRequest = { isExpandedDropDown = false }
            ) {
                (0..30).toList().forEach { day ->
                    DropdownMenuItem(
                        onClick = {
                            isExpandedDropDown = false
                            selectNewDaysBefore(notificationEvent.copy(
                                daysBeforeEvent = day
                            ))
                        },
                        text = {
                            Text(
                                text = LocalizedContext.current.resources
                                    .getQuantityString(R.plurals.days_number, day, day)
                            )
                        }
                    )

                }
            }
        }

        IconButton(
            onClick = {
                timeIconClick(notificationEvent)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.BrowseGallery,
                contentDescription = "Time notification"
            )
        }

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = LocalizedContext.current
                    .getString(R.string.notification_number, notificationEvent.id),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = notificationEvent.daysBeforeEvent.let {
                    val time =
                        "${notificationEvent.hour}:${notificationEvent.minute.let { if (it < 10) "0${it}" else it }}"
                    LocalizedContext.current.resources
                        .getQuantityString(R.plurals.notification_time_format, it, it, time)

                },
                fontWeight = FontWeight.Light,
                fontSize = 12.sp
            )
        }

        Switch(
            checked = notificationEvent.statusOn,
            onCheckedChange = { changeStatusNotification(notificationEvent.id) }
        )
    }
}

@Preview(showBackground = false)
@Composable
fun TimeReminderItemPreview() {
    TimeReminderItem(
        notificationEvent = NotificationEvent(
            id = 1,
            hour = 9,
            minute = 0,
            daysBeforeEvent = 3,
            statusOn = true
        ),
        changeStatusNotification = {},
        selectNewDaysBefore = {},
        timeIconClick = {}
    )
}
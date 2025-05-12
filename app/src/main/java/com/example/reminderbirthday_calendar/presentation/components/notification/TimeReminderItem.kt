package com.example.reminderbirthday_calendar.presentation.components.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.notification.NotificationEvent

@Composable
fun TimeReminderItem(
    modifier: Modifier = Modifier,
    notificationEvent: NotificationEvent,
    changeStatusNotification: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .then(modifier)
            .clickable(onClick = { changeStatusNotification(notificationEvent.id) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "Notification ${notificationEvent.id}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "${notificationEvent.daysBeforeEvent.let { if (it == 1) "$it day before" else "$it days before"}} " +
                        "at ${notificationEvent.hour}:${notificationEvent.minute.let { if (it < 10) "0${it}" else it }}",
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
        changeStatusNotification = {}
    )
}
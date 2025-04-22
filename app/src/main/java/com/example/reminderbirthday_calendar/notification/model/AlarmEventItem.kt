package com.example.reminderbirthday_calendar.notification.model

import java.time.LocalDateTime

data class AlarmEventItem(
    val dateTime: LocalDateTime,
    val message: String
)
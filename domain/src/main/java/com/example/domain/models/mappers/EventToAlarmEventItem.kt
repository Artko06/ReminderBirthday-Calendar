package com.example.domain.models.mappers

import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.models.notification.AlarmEventItem
import java.time.LocalDate

fun Event.toAlarmEventItem(): AlarmEventItem {
    val event = this
    val hourNotify = 9
    val minuteNotify = 0

    val nowDate = LocalDate.now()
    val nowYear = nowDate.year
    val nowDayOfYear = nowDate.dayOfYear
    var eventDayOfYear = event.originalDate.withYear(nowYear).dayOfYear
    val eventDate = this.originalDate.withYear(if (nowDayOfYear > eventDayOfYear) nowYear + 1 else nowYear)
    val alarmDateTime = eventDate.atTime(hourNotify, minuteNotify)


    val message = buildString {
        append("Congratulate to ${event.nameContact}")
        if (!event.surnameContact.isNullOrBlank()) append(" ${event.surnameContact}")
        if (event.eventType != EventType.OTHER) append(" with ${event.eventType.name.lowercase().replaceFirstChar { it.uppercase() }}")
        if (!event.notes.isNullOrBlank()) append(" — ${event.notes.take(20)}")
    }

    return AlarmEventItem(
        id = this.id,
        numberNotification = 1,
        message = message,
        dateTime = alarmDateTime
    )
}
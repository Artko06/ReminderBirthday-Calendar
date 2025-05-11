package com.example.domain.models.mappers

import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.models.notification.AlarmEventItem
import java.time.LocalDate

fun Event.toAlarmEventItem(
    numberNotification: Int,
    hourNotify: Int,
    minuteNotify: Int,
    daysBeforeEvent: Int
): AlarmEventItem {
    val event = this

    val nowDate = LocalDate.now()
    val nowYear = nowDate.year
    val nowDayOfYear = nowDate.dayOfYear
    val eventDayOfYear =
        event.originalDate.withYear(nowYear).minusDays(daysBeforeEvent.toLong()).dayOfYear
    var notificationDate = event.originalDate
        .withYear(if (nowDayOfYear > eventDayOfYear) nowYear + 1 else nowYear)
        .minusDays(daysBeforeEvent.toLong())

    val alarmDateTime = notificationDate.atTime(hourNotify, minuteNotify)


    val message = buildString {
        if (daysBeforeEvent != 0) append("Congratulate ${event.nameContact}")
        else append("CONGRATULATE ${event.nameContact}")
        if (!event.surnameContact.isNullOrBlank()) append(" ${event.surnameContact}")
        if (event.eventType != EventType.OTHER) {
            append(
                " with ${
                    event.eventType.name.lowercase().replaceFirstChar { it.uppercase() }
                }")
        }
        if (daysBeforeEvent != 0) {
            if (daysBeforeEvent == 1) append(" in $daysBeforeEvent day")
            else append(" in $daysBeforeEvent days")
        }
        if (!event.notes.isNullOrBlank()) {
            if (notes.length > 20) append(" — ${event.notes.take(20)}...")
            else append(" — ${event.notes.take(20)}")
        }
    }

    return AlarmEventItem(
        id = this.id,
        numberNotification = numberNotification,
        message = message,
        dateTime = alarmDateTime
    )
}
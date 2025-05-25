package com.example.domain.models.mappers

import com.example.domain.models.event.Event
import com.example.domain.models.notification.AlarmEventItem
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.YearMonth

fun List<Event>.toAlarmEventItem(
    numberNotification: Int,
    hourNotify: Int,
    minuteNotify: Int,
    daysBeforeEvent: Int
): AlarmEventItem {
    val events = this
    val originalDate = events.first().originalDate

    val nowDateTime = LocalDateTime.now()
    val currentYear = nowDateTime.year

    val eventMonth = originalDate.month
    val eventDay = originalDate.dayOfMonth
    val eventDateThisYear = getValidDate(currentYear, eventMonth, eventDay)

    val notificationDateTimeThisYear = eventDateThisYear
        .minusDays(daysBeforeEvent.toLong())
        .atTime(hourNotify, minuteNotify)

    val finalEventDate = if (notificationDateTimeThisYear.isBefore(nowDateTime)) {
        getValidDate(currentYear + 1, eventMonth, eventDay)
    } else {
        eventDateThisYear
    }

    val alarmDateTime = finalEventDate
        .minusDays(daysBeforeEvent.toLong())
        .atTime(hourNotify, minuteNotify)

    val message = events.joinToString(", ") {
            buildString {
                append(it.nameContact)
                if (!it.surnameContact.isNullOrBlank()) append(" ${it.surnameContact}")
            }
        }

    return AlarmEventItem(
        id = this.first().id,
        numberNotification = numberNotification,
        namesAlarmEvent = message,
        daysBeforeEvent = daysBeforeEvent,
        dateTime = alarmDateTime
    )
}

private fun getValidDate(year: Int, month: Month, day: Int): LocalDate {
    val maxDayOfMonth = YearMonth.of(year, month).lengthOfMonth()
    val safeDay = minOf(day, maxDayOfMonth)
    return LocalDate.of(year, month, safeDay)
}
package com.example.domain.util.extensionFunc

import com.example.domain.models.event.Event
import java.time.LocalDate

fun List<Event>.sortByClosestDate(): List<Event> {
    val nowLocalDate = LocalDate.now()
    val nowDayOfYear = nowLocalDate.dayOfYear
    val nowIsLeap = nowLocalDate.isLeapYear

    return this.sortedBy { event ->
        val eventDate = event.originalDate
        val eventDayOfYear = event.originalDate.withYear(nowLocalDate.year).dayOfYear

        if (eventDayOfYear >= nowDayOfYear) {
            eventDayOfYear - nowDayOfYear
        } else  if (nowIsLeap) 366 else 365 - nowDayOfYear + eventDate.withYear(nowLocalDate.year + 1).dayOfYear
    }
}
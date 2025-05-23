package com.example.domain.util.extensionFunc

import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun LocalDate.calculateDaysLeft(): Int {
    val nowLocalDate = LocalDate.now()
    val nowDayOfYear = nowLocalDate.dayOfYear
    val nowIsLeap = nowLocalDate.isLeapYear

    val eventDayOfYear = this.withYear(nowLocalDate.year).dayOfYear

    return if (eventDayOfYear >= nowDayOfYear) {
        eventDayOfYear - nowDayOfYear
    } else if (nowIsLeap) 366 else 365 - nowDayOfYear + this.withYear(nowLocalDate.year + 1).dayOfYear
}

fun LocalDate.calculateDaysLeftWithMyYear(year: Int): Int {
    val eventDate = this.withYear(year)
    val today = LocalDate.now()

    return ChronoUnit.DAYS.between(today, eventDate).toInt()
}
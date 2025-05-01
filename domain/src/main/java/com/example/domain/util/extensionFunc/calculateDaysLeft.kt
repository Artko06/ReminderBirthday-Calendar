package com.example.domain.util.extensionFunc

import java.time.LocalDate

fun LocalDate.calculateDaysLeft(): Int {
    val nowLocalDate = LocalDate.now()
    val nowDayOfYear = nowLocalDate.dayOfYear
    val nowIsLeap = nowLocalDate.isLeapYear

    val eventDayOfYear = this.withYear(nowLocalDate.year).dayOfYear

    return if (eventDayOfYear >= nowDayOfYear) {
        eventDayOfYear - nowDayOfYear
    } else if (nowIsLeap) 366 else 365 - nowDayOfYear + this.withYear(nowLocalDate.year + 1).dayOfYear
}
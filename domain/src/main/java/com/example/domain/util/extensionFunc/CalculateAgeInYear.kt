package com.example.domain.util.extensionFunc

import java.time.LocalDate

fun LocalDate.calculateAgeInYear(targetYear: Int): Int {
    val eventDate = this
    val eventDayOfYear = eventDate.dayOfYear

    val targetDate = eventDate.withYear(targetYear)
    val targetDayOfYear = targetDate.dayOfYear

    return if (targetDayOfYear > eventDayOfYear) targetYear - eventDate.year - 1
    else targetYear - eventDate.year
}
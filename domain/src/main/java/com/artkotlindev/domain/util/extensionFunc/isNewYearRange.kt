package com.artkotlindev.domain.util.extensionFunc

import java.time.LocalDate
import java.time.Month

fun isNewYearRange(): Boolean {
    val today = LocalDate.now()
    val startDate = LocalDate.of(today.year, Month.JANUARY, 1)
    val endDate = LocalDate.of(today.year, Month.JANUARY, 7)

    return !today.isBefore(startDate) && !today.isAfter(endDate)
}
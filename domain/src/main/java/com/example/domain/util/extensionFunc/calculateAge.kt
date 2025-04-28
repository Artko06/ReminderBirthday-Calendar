package com.example.domain.util.extensionFunc

import java.time.LocalDate

fun LocalDate.calculateAge(): Int{
    val nowLocalDate = LocalDate.now()
    val nowDayOfYear = nowLocalDate.dayOfYear

    val eventDate = this
    val eventDayOfYear = eventDate.withYear(nowLocalDate.year).dayOfYear

    return if (eventDayOfYear >= nowDayOfYear) nowLocalDate.year - eventDate.year - 1
    else nowLocalDate.year - eventDate.year
}
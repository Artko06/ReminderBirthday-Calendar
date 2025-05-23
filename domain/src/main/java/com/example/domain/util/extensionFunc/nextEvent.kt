package com.example.domain.util.extensionFunc

import java.time.LocalDate

fun LocalDate.nextEvent(): LocalDate {
    val today = LocalDate.now()
    var next = this.withYear(today.year)
    if (next.isBefore(today)) {
        next = this.withYear(today.year + 1)
    }
    return next
}

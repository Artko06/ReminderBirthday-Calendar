package com.example.domain.util.extensionFunc

import java.time.LocalDate

fun LocalDate.calculateNextAge(): Int {
    return this.calculateAge() + 1
}

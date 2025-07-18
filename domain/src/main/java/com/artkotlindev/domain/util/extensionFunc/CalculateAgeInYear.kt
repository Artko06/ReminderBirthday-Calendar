package com.artkotlindev.domain.util.extensionFunc

import java.time.LocalDate

fun LocalDate.calculateAgeInYear(targetYear: Int): Int {
    return if(targetYear < this.year) 0
    else targetYear - this.year
}
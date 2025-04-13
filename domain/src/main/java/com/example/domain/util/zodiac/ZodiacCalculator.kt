package com.example.domain.util.zodiac

import java.time.LocalDate

interface ZodiacCalculator {
    fun getWesternZodiac(date: LocalDate): String
    fun getChineseZodiac(date: LocalDate): String
}
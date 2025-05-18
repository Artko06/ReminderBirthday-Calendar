package com.example.domain.util.zodiac

import com.example.domain.models.zodiac.ChineseZodiac
import com.example.domain.models.zodiac.WesternZodiac
import java.time.LocalDate

interface ZodiacCalculator {
    fun getWesternZodiac(date: LocalDate): WesternZodiac
    fun getChineseZodiac(date: LocalDate): ChineseZodiac
}
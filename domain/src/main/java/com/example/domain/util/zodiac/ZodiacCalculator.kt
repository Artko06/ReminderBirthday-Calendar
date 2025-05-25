package com.example.domain.util.zodiac

import com.example.domain.models.zodiac.ChineseZodiac
import com.example.domain.models.zodiac.ZodiacSign
import java.time.LocalDate

interface ZodiacCalculator {
    fun getZodiacSign(date: LocalDate): ZodiacSign
    fun getChineseZodiac(date: LocalDate): ChineseZodiac
}
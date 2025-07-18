package com.artkotlindev.domain.util.zodiac

import com.artkotlindev.domain.models.zodiac.ChineseZodiac
import com.artkotlindev.domain.models.zodiac.ZodiacSign
import java.time.LocalDate

interface ZodiacCalculator {
    fun getZodiacSign(date: LocalDate): ZodiacSign
    fun getChineseZodiac(date: LocalDate): ChineseZodiac
}
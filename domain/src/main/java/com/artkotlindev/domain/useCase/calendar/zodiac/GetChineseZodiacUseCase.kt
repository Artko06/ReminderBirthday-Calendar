package com.artkotlindev.domain.useCase.calendar.zodiac

import com.artkotlindev.domain.models.zodiac.ChineseZodiac
import com.artkotlindev.domain.util.zodiac.ZodiacCalculator
import java.time.LocalDate

class GetChineseZodiacUseCase(private val zodiacCalculator: ZodiacCalculator) {
    operator fun invoke(date: LocalDate): ChineseZodiac = zodiacCalculator.getChineseZodiac(date = date)
}
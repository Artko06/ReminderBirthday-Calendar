package com.example.domain.useCase.calendar.zodiac

import com.example.domain.models.zodiac.ChineseZodiac
import com.example.domain.util.zodiac.ZodiacCalculator
import java.time.LocalDate

class GetChineseZodiacUseCase(private val zodiacCalculator: ZodiacCalculator) {
    operator fun invoke(date: LocalDate): ChineseZodiac = zodiacCalculator.getChineseZodiac(date = date)
}
package com.example.domain.useCase.calendar.zodiac

import com.example.domain.util.zodiac.ZodiacCalculator
import java.time.LocalDate

class GetWesternZodiacUseCase(private val zodiacCalculator: ZodiacCalculator) {
    operator fun invoke(date: LocalDate): String = zodiacCalculator.getWesternZodiac(date = date)
}
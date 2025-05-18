package com.example.domain.useCase.calendar.zodiac

import com.example.domain.models.zodiac.WesternZodiac
import com.example.domain.util.zodiac.ZodiacCalculator
import java.time.LocalDate

class GetWesternZodiacUseCase(private val zodiacCalculator: ZodiacCalculator) {
    operator fun invoke(date: LocalDate): WesternZodiac = zodiacCalculator.getWesternZodiac(date = date)
}
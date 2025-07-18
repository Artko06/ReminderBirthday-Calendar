package com.artkotlindev.domain.useCase.calendar.zodiac

import com.artkotlindev.domain.models.zodiac.ZodiacSign
import com.artkotlindev.domain.util.zodiac.ZodiacCalculator
import java.time.LocalDate

class GetWesternZodiacUseCase(private val zodiacCalculator: ZodiacCalculator) {
    operator fun invoke(date: LocalDate): ZodiacSign = zodiacCalculator.getZodiacSign(date = date)
}
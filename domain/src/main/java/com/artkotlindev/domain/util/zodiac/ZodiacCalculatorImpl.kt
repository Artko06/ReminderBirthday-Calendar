package com.artkotlindev.domain.util.zodiac

import com.artkotlindev.domain.models.zodiac.ChineseZodiac
import com.artkotlindev.domain.models.zodiac.ZodiacSign
import java.time.LocalDate

class ZodiacCalculatorImpl(

): ZodiacCalculator {
    override fun getZodiacSign(date: LocalDate): ZodiacSign {
        val day = date.dayOfMonth
        val month = date.monthValue

        return when {
            (month == 1 && day >= 21) || (month == 2 && day <= 19) -> ZodiacSign.AQUARIUS
            (month == 2 && day >= 20) || (month == 3 && day <= 20) -> ZodiacSign.PISCES
            (month == 3 && day >= 21) || (month == 4 && day <= 20) -> ZodiacSign.ARIES
            (month == 4 && day >= 21) || (month == 5 && day <= 20) -> ZodiacSign.TAURUS
            (month == 5 && day >= 21) || (month == 6 && day <= 21) -> ZodiacSign.GEMINI
            (month == 6 && day >= 22) || (month == 7 && day <= 22) -> ZodiacSign.CANCER
            (month == 7 && day >= 23) || (month == 8 && day <= 23) -> ZodiacSign.LEO
            (month == 8 && day >= 24) || (month == 9 && day <= 23) -> ZodiacSign.VIRGO
            (month == 9 && day >= 24) || (month == 10 && day <= 23) -> ZodiacSign.LIBRA
            (month == 10 && day >= 24) || (month == 11 && day <= 22) -> ZodiacSign.SCORPIO
            (month == 11 && day >= 23) || (month == 12 && day <= 21) -> ZodiacSign.SAGITTARIUS
            (month == 12 && day >= 22) || (month == 1 && day <= 20) -> ZodiacSign.CAPRICORN
            else -> ZodiacSign.AQUARIUS
        }
    }


    override fun getChineseZodiac(date: LocalDate): ChineseZodiac {
        return when(date.year % ChineseZodiac.entries.size){
            0 -> ChineseZodiac.MONKEY
            1 -> ChineseZodiac.ROOSTER
            2 -> ChineseZodiac.DOG
            3 -> ChineseZodiac.PIG
            4 -> ChineseZodiac.RAT
            5 -> ChineseZodiac.OX
            6 -> ChineseZodiac.TIGER
            7 -> ChineseZodiac.RABBIT
            8 -> ChineseZodiac.DRAGON
            9 -> ChineseZodiac.SNAKE
            10 -> ChineseZodiac.HORSE
            11 -> ChineseZodiac.GOAT
            else -> ChineseZodiac.MONKEY
        }
    }
}

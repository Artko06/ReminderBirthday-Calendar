package com.example.domain.util.zodiac

import com.example.domain.models.zodiac.ChineseZodiac
import com.example.domain.models.zodiac.WesternZodiac
import java.time.LocalDate

class ZodiacCalculatorImpl(

): ZodiacCalculator {
    override fun getWesternZodiac(date: LocalDate): WesternZodiac {
        val day = date.dayOfMonth
        val month = date.monthValue

        return when {
            (month == 1 && day >= 21) || (month == 2 && day <= 19) -> WesternZodiac.AQUARIUS
            (month == 2 && day >= 20) || (month == 3 && day <= 20) -> WesternZodiac.PISCES
            (month == 3 && day >= 21) || (month == 4 && day <= 20) -> WesternZodiac.ARIES
            (month == 4 && day >= 21) || (month == 5 && day <= 20) -> WesternZodiac.TAURUS
            (month == 5 && day >= 21) || (month == 6 && day <= 21) -> WesternZodiac.GEMINI
            (month == 6 && day >= 22) || (month == 7 && day <= 22) -> WesternZodiac.CANCER
            (month == 7 && day >= 23) || (month == 8 && day <= 23) -> WesternZodiac.LEO
            (month == 8 && day >= 24) || (month == 9 && day <= 23) -> WesternZodiac.VIRGO
            (month == 9 && day >= 24) || (month == 10 && day <= 23) -> WesternZodiac.LIBRA
            (month == 10 && day >= 24) || (month == 11 && day <= 22) -> WesternZodiac.SCORPIO
            (month == 11 && day >= 23) || (month == 12 && day <= 21) -> WesternZodiac.SAGITTARIUS
            (month == 12 && day >= 22) || (month == 1 && day <= 20) -> WesternZodiac.CAPRICORN
            else -> WesternZodiac.AQUARIUS
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

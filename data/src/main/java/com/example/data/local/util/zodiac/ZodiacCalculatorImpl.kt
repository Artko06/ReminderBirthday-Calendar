package com.example.data.local.util.zodiac

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.R
import com.example.domain.util.zodiac.ZodiacCalculator
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class ZodiacCalculatorImpl @Inject constructor(
    @ApplicationContext val context: Context
): ZodiacCalculator {
    override fun getWesternZodiac(date: LocalDate): String {
        return when(date.dayOfYear) {
            in 20..49 -> context.getString(R.string.zodiac_aquarius) // Aquarius
            in 50..80 -> context.getString(R.string.zodiac_pisces) // Pisces
            in 81..109 -> context.getString(R.string.zodiac_aries) // Aries
            in 110..140 -> context.getString(R.string.zodiac_taurus) // Taurus
            in 141..171 -> context.getString(R.string.zodiac_gemini) // Gemini
            in 172..202 -> context.getString(R.string.zodiac_cancer) // Cancer
            in 203..233 -> context.getString(R.string.zodiac_leo) // Leo
            in 234..264 -> context.getString(R.string.zodiac_virgo) // Virgo
            in 265..294 -> context.getString(R.string.zodiac_libra) // Libra
            in 295..324 -> context.getString(R.string.zodiac_scorpio) // Scorpio
            in 325..354 -> context.getString(R.string.zodiac_sagittarius) // Sagittarius
            in 355..366, in 1..19 -> context.getString(R.string.zodiac_capricorn) // Capricorn
            else -> ""
        }
    }

    override fun getChineseZodiac(date: LocalDate): String {
        val animals = context.resources.getStringArray(R.array.chinese_zodiac_animals)

        return animals[date.year % animals.size]
    }
}
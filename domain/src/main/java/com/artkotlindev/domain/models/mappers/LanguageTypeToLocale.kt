package com.artkotlindev.domain.models.mappers

import com.artkotlindev.domain.models.settings.LanguageType
import java.util.Locale

fun LanguageType.toLocale(): Locale = when (this) {
    LanguageType.SYSTEM -> Locale.getDefault()
    LanguageType.ENGLISH -> Locale.forLanguageTag("en")
    LanguageType.RUSSIAN -> Locale.forLanguageTag("ru")
    LanguageType.BELARUSIAN -> Locale.forLanguageTag("be")
}


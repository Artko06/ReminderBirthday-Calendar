package com.example.reminderbirthday_calendar.presentation.state

import com.example.domain.models.event.Event
import com.example.domain.models.zodiac.ChineseZodiac
import com.example.domain.models.zodiac.ZodiacSign
import java.time.LocalDate

data class EventDetailState(
    val event: Event = Event(
        id = 1,
        idContact = null,
        nameContact = "UNKNOWN",
        originalDate = LocalDate.now(),
        yearMatter = true
    ),

    val isShowDeleteDialog: Boolean = false,

    val statusZodiacSign: Boolean = false,
    val statusChineseZodiac: Boolean = false,
    val zodiacSign: ZodiacSign = ZodiacSign.AQUARIUS,
    val chineseZodiac: ChineseZodiac = ChineseZodiac.MONKEY,

    val isShowNotesDialog: Boolean = false,
    val newNotes: String = ""
)
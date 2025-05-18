package com.example.reminderbirthday_calendar.presentation.state

import com.example.domain.models.event.Event
import com.example.domain.models.zodiac.ChineseZodiac
import com.example.domain.models.zodiac.WesternZodiac
import java.time.LocalDate

data class EventDetailState(
    val event: Event = Event(
        id = 1,
        nameContact = "UNKNOWN",
        originalDate = LocalDate.now(),
        yearMatter = true
    ),

    val isShowDeleteDialog: Boolean = false,

    val statusWesternZodiac: Boolean = false,
    val statusChineseZodiac: Boolean = false,
    val westernZodiac: WesternZodiac = WesternZodiac.AQUARIUS,
    val chineseZodiac: ChineseZodiac = ChineseZodiac.MONKEY,

    val isShowNotesDialog: Boolean = false,
    val newNotes: String = ""
)
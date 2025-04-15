package com.example.domain.useCase.calendar.event

import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.repository.EventRepository
import java.time.LocalDate

class InsertEventUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(
        eventType: EventType,
        nameContact: String,
        surnameContact: String?,
        originalDate: LocalDate,
        yearMatter: Boolean,
        notes: String?,
        image: ByteArray?
    ) {
        val nowYear = LocalDate.now().year
        val month = originalDate.monthValue
        val day = originalDate.dayOfMonth
        val nextDate = LocalDate.of(nowYear, month, day)

        repository.upsertEvent(
            event = Event(
                id = 0,
                eventType = eventType,
                nameContact = nameContact,
                surnameContact = surnameContact,
                originalDate = originalDate,
                yearMatter = yearMatter,
                nextDate = nextDate,
                notes = notes,
                image = image
            )
        )
    }
}
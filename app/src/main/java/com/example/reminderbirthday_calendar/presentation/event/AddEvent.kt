package com.example.reminderbirthday_calendar.presentation.event

import com.example.domain.models.event.EventType
import com.example.domain.models.event.SortTypeEvent
import java.time.LocalDate

sealed class AddEvent {
    object ShowDatePickerDialog: AddEvent()
    object CloseDatePickerDialog: AddEvent()

    data class OnPickPhoto(val photo: ByteArray?): AddEvent() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as OnPickPhoto

            if (!photo.contentEquals(other.photo)) return false

            return true
        }

        override fun hashCode(): Int {
            return photo?.contentHashCode() ?: 0
        }
    }

    data class ChangeEventType(val eventType: EventType): AddEvent()
    data class ChangeSortType(val sortType: SortTypeEvent): AddEvent()
    data class ChangeValueName(val name: String): AddEvent()
    data class ChangeValueSurname(val surname: String): AddEvent()
    data class ChangeDate(val date: LocalDate): AddEvent()
    object ChangeYearMatter: AddEvent()
    data class ChangeNotes(val notes: String): AddEvent()

    object AddEventButton: AddEvent()
}
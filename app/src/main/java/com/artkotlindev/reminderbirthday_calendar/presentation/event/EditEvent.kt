package com.artkotlindev.reminderbirthday_calendar.presentation.event

import com.artkotlindev.domain.models.event.ContactInfo
import com.artkotlindev.domain.models.event.SortTypeEvent
import java.time.LocalDate

sealed class EditEvent {
    object ShowDatePickerDialog: EditEvent()
    object CloseDatePickerDialog: EditEvent()

    object ShowListContacts: EditEvent()
    object CloseListContacts: EditEvent()
    data class OnSelectContact(val contact: ContactInfo): EditEvent()

    data class OnPickPhoto(val photo: ByteArray?): EditEvent() {
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

    data class ChangeSortType(val sortType: SortTypeEvent): EditEvent()
    data class ChangeValueName(val name: String): EditEvent()
    data class ChangeValueSurname(val surname: String): EditEvent()
    data class ChangeDate(val date: LocalDate): EditEvent()
    object ChangeYearMatter: EditEvent()

    object SaveEventButton: EditEvent()
}
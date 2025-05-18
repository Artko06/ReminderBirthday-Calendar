package com.example.reminderbirthday_calendar.presentation.state

import com.example.domain.models.event.ContactInfo
import com.example.domain.models.event.EventType
import com.example.domain.models.event.SortTypeEvent
import java.time.LocalDate

data class EditEventState(
    val listContacts: List<ContactInfo> = emptyList(),

    val id: Long = 0,
    val eventType: EventType = EventType.BIRTHDAY,
    val sortType: SortTypeEvent = SortTypeEvent.RELATIVE,
    val name: String = "",
    val surname: String? = null,
    val date: LocalDate = LocalDate.now(),
    val yearMatter: Boolean = true,
    val notes: String? = null,
    val pickedPhoto: ByteArray? = null,

    val isLoadingContactList: Boolean = false,

    val isShowDatePicker: Boolean = false,
    val isShowListContacts: Boolean = false,

    val isSaveButtonEnable: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EditEventState

        if (id != other.id) return false
        if (yearMatter != other.yearMatter) return false
        if (isLoadingContactList != other.isLoadingContactList) return false
        if (isShowDatePicker != other.isShowDatePicker) return false
        if (isShowListContacts != other.isShowListContacts) return false
        if (isSaveButtonEnable != other.isSaveButtonEnable) return false
        if (listContacts != other.listContacts) return false
        if (eventType != other.eventType) return false
        if (sortType != other.sortType) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (date != other.date) return false
        if (notes != other.notes) return false
        if (!pickedPhoto.contentEquals(other.pickedPhoto)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + yearMatter.hashCode()
        result = 31 * result + isLoadingContactList.hashCode()
        result = 31 * result + isShowDatePicker.hashCode()
        result = 31 * result + isShowListContacts.hashCode()
        result = 31 * result + isSaveButtonEnable.hashCode()
        result = 31 * result + listContacts.hashCode()
        result = 31 * result + eventType.hashCode()
        result = 31 * result + sortType.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (surname?.hashCode() ?: 0)
        result = 31 * result + date.hashCode()
        result = 31 * result + (notes?.hashCode() ?: 0)
        result = 31 * result + (pickedPhoto?.contentHashCode() ?: 0)
        return result
    }
}
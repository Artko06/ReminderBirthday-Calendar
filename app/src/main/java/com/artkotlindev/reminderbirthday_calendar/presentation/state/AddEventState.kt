package com.artkotlindev.reminderbirthday_calendar.presentation.state

import com.artkotlindev.domain.models.event.ContactInfo
import com.artkotlindev.domain.models.event.EventType
import com.artkotlindev.domain.models.event.SortTypeEvent
import java.time.LocalDate

data class AddEventState (
    val isShowDatePicker: Boolean = false,
    val isShowListContacts: Boolean = false,

    val isLoadingContactList: Boolean = false,
    val readNameContact: String = "",
    val readSurnameContact: String = "",
    val listContacts: List<ContactInfo> = emptyList(),
    val idSelectedContact: String? = null,
    val pickedPhoto: ByteArray? = null,
    val sortType: SortTypeEvent = SortTypeEvent.FAMILY,
    val eventType: EventType = EventType.BIRTHDAY,
    val valueName: String = "",
    val valueSurname: String = "",
    val date: LocalDate? = null,
    val yearMatter: Boolean = true,
    val notes: String = "",

    val isEnableAddEventButton: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AddEventState

        if (isShowDatePicker != other.isShowDatePicker) return false
        if (isShowListContacts != other.isShowListContacts) return false
        if (isLoadingContactList != other.isLoadingContactList) return false
        if (yearMatter != other.yearMatter) return false
        if (isEnableAddEventButton != other.isEnableAddEventButton) return false
        if (readNameContact != other.readNameContact) return false
        if (readSurnameContact != other.readSurnameContact) return false
        if (listContacts != other.listContacts) return false
        if (idSelectedContact != other.idSelectedContact) return false
        if (!pickedPhoto.contentEquals(other.pickedPhoto)) return false
        if (sortType != other.sortType) return false
        if (eventType != other.eventType) return false
        if (valueName != other.valueName) return false
        if (valueSurname != other.valueSurname) return false
        if (date != other.date) return false
        if (notes != other.notes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isShowDatePicker.hashCode()
        result = 31 * result + isShowListContacts.hashCode()
        result = 31 * result + isLoadingContactList.hashCode()
        result = 31 * result + yearMatter.hashCode()
        result = 31 * result + isEnableAddEventButton.hashCode()
        result = 31 * result + readNameContact.hashCode()
        result = 31 * result + readSurnameContact.hashCode()
        result = 31 * result + listContacts.hashCode()
        result = 31 * result + (idSelectedContact?.hashCode() ?: 0)
        result = 31 * result + (pickedPhoto?.contentHashCode() ?: 0)
        result = 31 * result + sortType.hashCode()
        result = 31 * result + eventType.hashCode()
        result = 31 * result + valueName.hashCode()
        result = 31 * result + valueSurname.hashCode()
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + notes.hashCode()
        return result
    }
}
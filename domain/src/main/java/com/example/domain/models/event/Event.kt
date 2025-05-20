package com.example.domain.models.event

import java.time.LocalDate

data class Event(
    val id: Long,
    val idContact: String?,
    val eventType: EventType = EventType.BIRTHDAY,
    val sortTypeEvent: SortTypeEvent = SortTypeEvent.RELATIVE,
    val nameContact: String,
    val surnameContact: String? = null,
    val originalDate: LocalDate,
    val yearMatter : Boolean,
    val notes: String? = null,
    val image: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        if (id != other.id) return false
        if (yearMatter != other.yearMatter) return false
        if (idContact != other.idContact) return false
        if (eventType != other.eventType) return false
        if (sortTypeEvent != other.sortTypeEvent) return false
        if (nameContact != other.nameContact) return false
        if (surnameContact != other.surnameContact) return false
        if (originalDate != other.originalDate) return false
        if (notes != other.notes) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + yearMatter.hashCode()
        result = 31 * result + (idContact?.hashCode() ?: 0)
        result = 31 * result + eventType.hashCode()
        result = 31 * result + sortTypeEvent.hashCode()
        result = 31 * result + nameContact.hashCode()
        result = 31 * result + (surnameContact?.hashCode() ?: 0)
        result = 31 * result + originalDate.hashCode()
        result = 31 * result + (notes?.hashCode() ?: 0)
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}

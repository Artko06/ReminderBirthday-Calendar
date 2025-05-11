package com.example.data.local.util.serialization

data class EventSerializable(
    val id: Long,
    val eventType: String,
    val sortTypeEvent: String,
    val nameContact: String,
    val surnameContact: String?,
    val originalDate: String,
    val yearMatter: Boolean,
    val notes: String? = null,
    val image: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EventSerializable

        if (id != other.id) return false
        if (yearMatter != other.yearMatter) return false
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
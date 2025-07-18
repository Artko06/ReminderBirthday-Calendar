package com.artkotlindev.domain.models.event

data class ImportedEvent(
    val id: String,
    val name: String,
    val surname: String,
    val eventDate: String,
    val image: ByteArray? = null,
    val eventType: EventType = EventType.BIRTHDAY,
    val customLabel: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImportedEvent

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (eventDate != other.eventDate) return false
        if (!image.contentEquals(other.image)) return false
        if (eventType != other.eventType) return false
        if (customLabel != other.customLabel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + eventDate.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + eventType.hashCode()
        result = 31 * result + (customLabel?.hashCode() ?: 0)
        return result
    }
}
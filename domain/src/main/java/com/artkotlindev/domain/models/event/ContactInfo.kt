package com.artkotlindev.domain.models.event

data class ContactInfo(
    val id: String,
    val name: String,
    val surname: String,
    val phone: String,
    val image: ByteArray?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContactInfo

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (phone != other.phone) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}

package com.example.domain.models.event

data class ContactInfo(
    val id: String,
    val name: String,
    val surname: String,
    val image: ByteArray?,
) {
    val fullName: String
        get() =
            if (surname.isBlank())
                name
            else
                "$name,$surname".replace("\\s".toRegex(), " ")

    override fun toString() = "$name $surname"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContactInfo

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}

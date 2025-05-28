package com.example.data.local.entity.event

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.models.event.SortTypeEvent
import java.time.LocalDate

@Entity(
    tableName = "event",
    indices = [Index(
        value = arrayOf("nameContact", "surnameContact", "originalDate", "eventType"),
        unique = true
    )]
)
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val idContact: String?,
    val eventType: String,
    val sortType: String,
    val nameContact: String,
    val surnameContact: String?,
    val originalDate: String,
    val yearMatter: Boolean,
    val notes: String? = null,
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB) // Binary data
    val image: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EventEntity

        if (id != other.id) return false
        if (yearMatter != other.yearMatter) return false
        if (idContact != other.idContact) return false
        if (eventType != other.eventType) return false
        if (sortType != other.sortType) return false
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
        result = 31 * result + sortType.hashCode()
        result = 31 * result + nameContact.hashCode()
        result = 31 * result + (surnameContact?.hashCode() ?: 0)
        result = 31 * result + originalDate.hashCode()
        result = 31 * result + (notes?.hashCode() ?: 0)
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}

fun EventEntity.toDomain() = Event(
    id = id,
    idContact = idContact,
    eventType = enumValueOf<EventType>(eventType),
    sortTypeEvent = enumValueOf<SortTypeEvent>(sortType),
    nameContact = nameContact,
    surnameContact = surnameContact,
    originalDate = LocalDate.parse(originalDate),
    yearMatter = yearMatter,
    notes = notes,
    image = image
)

fun Event.toData() = EventEntity(
    id = id,
    idContact = idContact,
    eventType = eventType.name,
    sortType = sortTypeEvent.name,
    nameContact = nameContact,
    surnameContact = surnameContact,
    originalDate = originalDate.toString(),
    yearMatter = yearMatter,
    notes = notes,
    image = image
)

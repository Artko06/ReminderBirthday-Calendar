package com.example.data.local.util.serialization

import com.example.data.local.entity.settings.event.EventEntity
import com.example.serializationmodule.model.EventSerializable

fun EventSerializable.toEventEntity() = EventEntity(
    id = id,
    eventType = eventType,
    nameContact = nameContact,
    surnameContact = surnameContact,
    originalDate = originalDate,
    yearMatter = yearMatter,
    nextDate = nextDate,
    notes = notes,
    image = image
)

fun EventEntity.toEventSerializable() = EventSerializable(
    id = id,
    eventType = eventType,
    nameContact = nameContact,
    surnameContact = surnameContact,
    originalDate = originalDate,
    yearMatter = yearMatter,
    nextDate = nextDate,
    notes = notes,
    image = image
)
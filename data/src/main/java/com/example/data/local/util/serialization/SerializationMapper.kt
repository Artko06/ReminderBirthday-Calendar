package com.example.data.local.util.serialization

import com.example.data.local.entity.settings.event.EventEntity

fun EventSerializable.toEventEntity() = EventEntity(
    id = id,
    idContact = idContact,
    eventType = eventType,
    sortType = sortTypeEvent,
    nameContact = nameContact,
    surnameContact = surnameContact,
    originalDate = originalDate,
    yearMatter = yearMatter,
    notes = notes,
    image = image
)

fun EventEntity.toEventSerializable() = EventSerializable(
    id = id,
    idContact = idContact,
    eventType = eventType,
    sortTypeEvent = sortType,
    nameContact = nameContact,
    surnameContact = surnameContact,
    originalDate = originalDate,
    yearMatter = yearMatter,
    notes = notes,
    image = image
)
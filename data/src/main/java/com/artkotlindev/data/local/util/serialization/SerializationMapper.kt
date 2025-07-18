package com.artkotlindev.data.local.util.serialization

import com.artkotlindev.data.local.entity.event.EventEntity

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
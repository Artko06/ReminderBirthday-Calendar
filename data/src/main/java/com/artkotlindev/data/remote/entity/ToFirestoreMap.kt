package com.artkotlindev.data.remote.entity

import com.artkotlindev.data.local.entity.event.EventEntity

fun EventEntity.toFirestoreMap(): Map<String, Any?> {
    return mapOf(
        "id" to id,
        "eventType" to eventType,
        "sortTypeEvent" to sortType,
        "nameContact" to nameContact,
        "surnameContact" to surnameContact,
        "originalDate" to originalDate,
        "yearMatter" to yearMatter,
        "notes" to notes,
    )
}
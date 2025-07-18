package com.artkotlindev.data.remote.entity

import com.artkotlindev.data.local.entity.event.EventEntity

fun firestoreMapToEventEntity(event: Map<String, Any?>): EventEntity {
    return EventEntity(
        id = (event["id"] as Long),
        idContact = event["idContact"] as? String,
        eventType = event["eventType"] as String,
        sortType = event["sortTypeEvent"] as String,
        nameContact = event["nameContact"] as String,
        surnameContact = event["surnameContact"] as? String,
        originalDate = event["originalDate"] as String,
        yearMatter = event["yearMatter"] as Boolean,
        notes = event["notes"] as? String,
        image = null
    )
}
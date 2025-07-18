package com.artkotlindev.domain.util.extensionFunc

import com.artkotlindev.domain.models.event.Event

fun List<Event>.sortByClosestDate(): List<Event> {
    return this.sortedBy { event ->
        event.originalDate.calculateDaysLeft()
    }
}
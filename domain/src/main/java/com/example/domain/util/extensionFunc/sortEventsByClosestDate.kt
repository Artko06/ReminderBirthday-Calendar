package com.example.domain.util.extensionFunc

import com.example.domain.models.event.Event

fun List<Event>.sortByClosestDate(): List<Event> {
    return this.sortedBy { event ->
        event.originalDate.calculateDaysLeft()
    }
}
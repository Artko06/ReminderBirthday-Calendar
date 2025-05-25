package com.example.domain.models.notification

import java.time.LocalDateTime

data class AlarmEventItem(
    val id: Long,
    val numberNotification: Int, // max 9
    val dateTime: LocalDateTime,
    val daysBeforeEvent: Int,
    val namesAlarmEvent: String
){
    override fun hashCode(): Int {
        return "${id}:${numberNotification}".hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlarmEventItem

        if (id != other.id) return false
        if (numberNotification != other.numberNotification) return false
        if (daysBeforeEvent != other.daysBeforeEvent) return false
        if (dateTime != other.dateTime) return false
        if (namesAlarmEvent != other.namesAlarmEvent) return false

        return true
    }
}
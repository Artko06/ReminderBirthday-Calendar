package com.example.domain.models.notification

import java.time.LocalDateTime

data class AlarmEventItem(
    val id: Long,
    val numberNotification: Int, // max 9
    val dateTime: LocalDateTime,
    val message: String
){
    override fun hashCode(): Int {
        return "${id}:${numberNotification}".hashCode()
    }
}
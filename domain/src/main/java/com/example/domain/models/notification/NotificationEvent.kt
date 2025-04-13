package com.example.domain.models.notification

data class NotificationEvent(
    val id: Int,
    val hour: Int = 9,
    val minute: Int = 0,
    val daysBeforeEvent: Int = 0
)
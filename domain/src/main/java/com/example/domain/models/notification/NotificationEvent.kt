package com.example.domain.models.notification

data class NotificationEvent(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val daysBeforeEvent: Int,
    val statusOn: Boolean
)
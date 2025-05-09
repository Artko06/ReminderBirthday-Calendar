package com.example.domain.repository

import com.example.domain.models.notification.AlarmEventItem

interface AlarmEventScheduler {
    fun schedule(item: AlarmEventItem)
    fun cancel(item: AlarmEventItem)
}
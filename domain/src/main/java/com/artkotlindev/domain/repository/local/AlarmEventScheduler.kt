package com.artkotlindev.domain.repository.local

import com.artkotlindev.domain.models.notification.AlarmEventItem

interface AlarmEventScheduler {
    fun schedule(item: AlarmEventItem)
    fun cancel(item: AlarmEventItem)
}
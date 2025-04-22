package com.example.reminderbirthday_calendar.notification.schedulerInterface

import com.example.reminderbirthday_calendar.notification.model.AlarmEventItem

interface AlarmEventScheduler {
    fun schedule(item: AlarmEventItem)
    fun cancel(item: AlarmEventItem)
}
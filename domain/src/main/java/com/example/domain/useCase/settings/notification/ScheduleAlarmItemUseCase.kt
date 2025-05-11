package com.example.domain.useCase.settings.notification

import com.example.domain.models.notification.AlarmEventItem
import com.example.domain.repository.AlarmEventScheduler
import java.time.LocalDateTime

class ScheduleAlarmItemUseCase(
    private val scheduler: AlarmEventScheduler
) {
    operator fun invoke(item: AlarmEventItem) {
        scheduler.schedule(item = item)
    }

    operator fun invoke(
        id: Long,
        numberNotification: Int,
        dateTime: LocalDateTime,
        message: String
    ) {
        val alarmItem = AlarmEventItem(
            id = id,
            numberNotification = numberNotification,
            dateTime = dateTime,
            message = message
        )

        scheduler.schedule(item = alarmItem)
    }
}
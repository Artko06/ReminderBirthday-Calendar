package com.artkotlindev.domain.useCase.settings.notification

import com.artkotlindev.domain.models.notification.AlarmEventItem
import com.artkotlindev.domain.repository.local.AlarmEventScheduler
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
        daysBeforeEvent: Int,
        namesAlarmEvent: String
    ) {
        val alarmItem = AlarmEventItem(
            id = id,
            numberNotification = numberNotification,
            daysBeforeEvent = daysBeforeEvent,
            dateTime = dateTime,
            namesAlarmEvent = namesAlarmEvent
        )

        scheduler.schedule(item = alarmItem)
    }
}
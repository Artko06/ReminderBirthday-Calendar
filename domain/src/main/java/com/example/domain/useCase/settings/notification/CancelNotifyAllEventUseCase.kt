package com.example.domain.useCase.settings.notification

import com.example.domain.models.mappers.toAlarmEventItem
import com.example.domain.repository.AlarmEventScheduler
import com.example.domain.repository.EventRepository
import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class CancelNotifyAllEventUseCase(
    private val alarmRepository: AlarmEventScheduler,
    private val eventRepository: EventRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke() {
        val allEvent = eventRepository.getAllEvents().first()

        val groupEvents = allEvent.groupBy { it.originalDate.withYear(LocalDate.now().year) }

        settingsRepository.getAllNotificationEvent().first().distinct().forEach { notification ->
            if (notification.statusOn){
                groupEvents.forEach { date, events ->
                    alarmRepository.cancel(item = events.toAlarmEventItem(
                        numberNotification = notification.id,
                        hourNotify = notification.hour,
                        minuteNotify = notification.minute,
                        daysBeforeEvent = notification.daysBeforeEvent
                    ))
                }
            }
        }
    }
}
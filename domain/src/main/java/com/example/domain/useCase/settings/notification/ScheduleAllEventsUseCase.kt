package com.example.domain.useCase.settings.notification

import com.example.domain.models.mappers.toAlarmEventItem
import com.example.domain.repository.local.AlarmEventScheduler
import com.example.domain.repository.local.EventRepository
import com.example.domain.repository.local.SettingsRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class ScheduleAllEventsUseCase(
    private val alarmRepository: AlarmEventScheduler,
    private val eventRepository: EventRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke() {
        val notificationEvents = settingsRepository.getAllNotificationEvent().first().distinct()
        val allEvent = eventRepository.getAllEvents().first()

        val groupEvents = allEvent.groupBy { it.originalDate.withYear(LocalDate.now().year) }

        notificationEvents.forEach { notification ->
            if (notification.statusOn) {
                groupEvents.forEach { date, events ->
                    alarmRepository.schedule(item = events.toAlarmEventItem(
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
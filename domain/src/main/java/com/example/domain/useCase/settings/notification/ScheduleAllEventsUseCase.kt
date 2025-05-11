package com.example.domain.useCase.settings.notification

import com.example.domain.models.mappers.toAlarmEventItem
import com.example.domain.repository.AlarmEventScheduler
import com.example.domain.repository.EventRepository
import com.example.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first

class ScheduleAllEventsUseCase(
    private val alarmRepository: AlarmEventScheduler,
    private val eventRepository: EventRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke() {
        val notificationEvents = settingsRepository.getAllNotificationEvent().first()
        val allEvent = eventRepository.getAllEvents().first()

        notificationEvents.forEach { notification ->
            if (notification.statusOn) {
                allEvent.forEach { event ->
                    alarmRepository.schedule(item = event.toAlarmEventItem(
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
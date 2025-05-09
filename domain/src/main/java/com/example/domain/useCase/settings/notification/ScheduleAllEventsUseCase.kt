package com.example.domain.useCase.settings.notification

import com.example.domain.models.mappers.toAlarmEventItem
import com.example.domain.repository.AlarmEventScheduler
import com.example.domain.repository.EventRepository
import kotlinx.coroutines.flow.first

class ScheduleAllEventsUseCase(
    private val alarmRepository: AlarmEventScheduler,
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke() {
        val allEvent = eventRepository.getAllEvents().first()

        allEvent.forEach { event ->
            alarmRepository.schedule(item = event.toAlarmEventItem())
        }
    }
}
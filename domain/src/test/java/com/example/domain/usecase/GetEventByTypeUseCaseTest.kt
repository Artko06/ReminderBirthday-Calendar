package com.example.domain.usecase

import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.repository.EventRepository
import com.example.domain.useCase.calendar.event.GetEventByTypeUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate


class GetEventByTypeUseCaseTest {

    private val repository = mock<EventRepository>()
    private val useCase = GetEventByTypeUseCase(repository)

    @AfterEach
    fun tearDown(){
        Mockito.reset(repository)
    }

    @Test
    fun `invoke should return flow of events of specific type`() = runTest {
        val eventType = EventType.BIRTHDAY
        val mockEvents = listOf(
            Event(
                id = 1,
                eventType = eventType,
                nameContact = "Alice",
                surnameContact = null,
                originalDate = LocalDate.of(1990, 6, 15),
                yearMatter = true,
                notes = null,
                image = null
            )
        )

        whenever(repository.getByTypeEvents(eventType.name)).thenReturn(flowOf(mockEvents))

        val result = useCase(eventType).first()

        assertEquals(mockEvents, result)
        verify(repository).getByTypeEvents(eventType.name)
    }
}

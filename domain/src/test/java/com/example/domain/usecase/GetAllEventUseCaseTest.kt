package com.example.domain.usecase

import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.repository.local.EventRepository
import com.example.domain.useCase.calendar.event.GetAllEventUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate

class GetAllEventUseCaseTest {

    val repository = mock<EventRepository>()
    val useCase = GetAllEventUseCase(repository = repository)

    @AfterEach
    fun tearDown(){
        Mockito.reset(repository)
    }

    @Test
    fun `invoke should return flow of events from repository`() = runTest {
        val mockEvents = listOf(
            Event(
                id = 1,
                idContact = null,
                eventType = EventType.BIRTHDAY,
                nameContact = "Test1",
                surnameContact = null,
                originalDate = LocalDate.of(2000, 12, 10),
                yearMatter = true,
                notes = null,
                image = null
            ),
            Event(
                id = 2,
                idContact = null,
                eventType = EventType.BIRTHDAY,
                nameContact = "Test2",
                surnameContact = null,
                originalDate = LocalDate.of(2000,1,1),
                yearMatter = true,
                notes = null,
                image = null
            )
        )
        whenever(repository.getAllEvents()).thenReturn(flowOf(mockEvents))

        val result = useCase().first()

        assertEquals(result, mockEvents)
    }
}
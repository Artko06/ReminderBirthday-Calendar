package com.example.domain.usecase

import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.repository.local.EventRepository
import com.example.domain.useCase.calendar.event.GetEventByContactNameUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate

class GetEventByContactNameUseCaseTest {

    private val repository = mock<EventRepository>()
    private val useCase = GetEventByContactNameUseCase(repository)

    @AfterEach
    fun tearDown(){
        Mockito.reset(repository)
    }

    private val mockEvents = listOf(
        Event(
            id = 1,
            idContact = null,
            eventType = EventType.BIRTHDAY,
            nameContact = "Alice",
            surnameContact = "Johnson",
            originalDate = LocalDate.of(1990, 1, 1),
            yearMatter = true,
            notes = null,
            image = null
        ),
        Event(
            id = 2,
            idContact = null,
            eventType = EventType.ANNIVERSARY,
            nameContact = "Bob",
            surnameContact = "Smith",
            originalDate = LocalDate.of(1995, 5, 15),
            yearMatter = true,
            notes = null,
            image = null
        ),
        Event(
            id = 3,
            idContact = null,
            eventType = EventType.BIRTHDAY,
            nameContact = "Charlie",
            surnameContact = null,
            originalDate = LocalDate.of(2000, 3, 10),
            yearMatter = false,
            notes = null,
            image = null
        )
    )

    @Test
    fun `invoke with blank string should return all events`() = runTest {
        whenever(repository.getAllEvents()).thenReturn(flowOf(mockEvents))

        val result = useCase("").first()

        assertEquals(mockEvents, result)
    }

    @Test
    fun `invoke with partial name should return matching events`() = runTest {
        whenever(repository.getAllEvents()).thenReturn(flowOf(mockEvents))

        val result = useCase("ali").first()

        assertEquals(1, result.size)
        assertEquals("Alice", result[0].nameContact)
    }

    @Test
    fun `invoke with full name should return matching event`() = runTest {
        whenever(repository.getAllEvents()).thenReturn(flowOf(mockEvents))

        val result = useCase("bob smith").first()

        assertEquals(1, result.size)
        assertEquals("Bob", result[0].nameContact)
        assertEquals("Smith", result[0].surnameContact)
    }

    @Test
    fun `invoke with name not in list should return empty list`() = runTest {
        whenever(repository.getAllEvents()).thenReturn(flowOf(mockEvents))

        val result = useCase("nonexistent").first()

        assertTrue(result.isEmpty())
    }
}

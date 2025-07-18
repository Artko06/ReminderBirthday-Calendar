package com.artkotlindev.domain.usecase

import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.models.event.EventType
import com.artkotlindev.domain.repository.local.EventRepository
import com.artkotlindev.domain.useCase.calendar.event.UpsertEventsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

class UpsertEventsUseCaseTest {

    private val repository = mock<EventRepository>()
    private val useCase = UpsertEventsUseCase(repository)

    @AfterEach
    fun tearDown(){
        Mockito.reset(repository)
    }

    @Test
    fun `invoke should return list of results from repository`() = runTest {
        // given
        val events = listOf(
            Event(
                id = 0,
                idContact = null,
                eventType = EventType.BIRTHDAY,
                nameContact = "Alice",
                surnameContact = "Doe",
                originalDate = LocalDate.of(1990, 1, 1),
                yearMatter = true,
                notes = null,
                image = null
            ),
            Event(
                id = 0,
                idContact = null,
                eventType = EventType.ANNIVERSARY,
                nameContact = "Bob",
                surnameContact = null,
                originalDate = LocalDate.of(1980, 12, 31),
                yearMatter = false,
                notes = "Work",
                image = null
            )
        )

        val expectedResults = listOf(true, false)
        whenever(repository.upsertEvents(events)).thenReturn(expectedResults)

        // when
        val result = useCase(events)

        // then
        assertEquals(expectedResults, result)
        verify(repository).upsertEvents(events)
    }

    @Test
    fun `invoke should return empty list when events list is empty`() = runTest {
        // given
        val events = emptyList<Event>()
        whenever(repository.upsertEvents(events)).thenReturn(emptyList())

        // when
        val result = useCase(events)

        // then
        assertTrue(result.isEmpty())
        verify(repository).upsertEvents(events)
    }
}

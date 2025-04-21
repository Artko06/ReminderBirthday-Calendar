package com.example.domain.usecase

import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.repository.EventRepository
import com.example.domain.useCase.calendar.event.DeleteEventUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate


class DeleteEventUseCaseTest {

    private val repository = mock<EventRepository>()
    private val useCase = DeleteEventUseCase(repository)

    @AfterEach
    fun tearDown(){
        Mockito.reset(repository)
    }

    private val mockEvent = Event(
        id = 42,
        eventType = EventType.BIRTHDAY,
        nameContact = "Test",
        surnameContact = null,
        originalDate = LocalDate.of(2000, 1, 1),
        yearMatter = true,
        nextDate = LocalDate.of(2025, 1, 1),
        notes = null,
        image = null
    )

    @Test
    fun `invoke should delete event when found`() = runTest {
        whenever(repository.getByIdEvents(42)).thenReturn(flowOf(mockEvent))
        whenever(repository.deleteEvent(mockEvent)).thenReturn(true)

        val result = useCase(42)

        assertTrue(result)
        verify(repository).deleteEvent(mockEvent)
    }

    @Test
    fun `invoke should return false when event not found`() = runTest {
        whenever(repository.getByIdEvents(999)).thenReturn(flowOf(null))

        val result = useCase(999)

        assertFalse(result)
        verify(repository, never()).deleteEvent(any())
    }
}
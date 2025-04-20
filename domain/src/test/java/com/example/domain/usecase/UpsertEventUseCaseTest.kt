package com.example.domain.usecase

import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.repository.EventRepository
import com.example.domain.useCase.calendar.event.UpsertEventUseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

class UpsertEventUseCaseTest {

    private val repository = mock<EventRepository>()
    private val useCase = UpsertEventUseCase(repository)

    @Test
    fun `invoke should call repository with correct Event and return true`() = runTest {
        // given
        val eventType = EventType.BIRTHDAY
        val name = "Alice"
        val surname = "Smith"
        val originalDate = LocalDate.of(1990, 5, 10)
        val yearMatter = true
        val notes = "Friend"
        val image = byteArrayOf(1, 2, 3)

        val nowYear = LocalDate.now().year
        val expectedNextDate = LocalDate.of(nowYear, 5, 10)

        val expectedEvent = Event(
            id = 0,
            eventType = eventType,
            nameContact = name,
            surnameContact = surname,
            originalDate = originalDate,
            yearMatter = yearMatter,
            nextDate = expectedNextDate,
            notes = notes,
            image = image
        )

        whenever(repository.upsertEvent(expectedEvent)).thenReturn(true)

        // when
        val result = useCase(
            eventType = eventType,
            nameContact = name,
            surnameContact = surname,
            originalDate = originalDate,
            yearMatter = yearMatter,
            notes = notes,
            image = image
        )

        // then
        assertTrue(result)
        verify(repository).upsertEvent(expectedEvent)
    }

    @Test
    fun `invoke should return false when repository returns false`() = runTest {
        // given
        val eventType = EventType.OTHER
        val name = "Bob"
        val surname = null
        val originalDate = LocalDate.of(1985, 8, 20)
        val yearMatter = false
        val notes = null
        val image = null

        val nowYear = LocalDate.now().year
        val expectedNextDate = LocalDate.of(nowYear, 8, 20)

        val expectedEvent = Event(
            id = 0,
            eventType = eventType,
            nameContact = name,
            surnameContact = null,
            originalDate = originalDate,
            yearMatter = yearMatter,
            nextDate = expectedNextDate,
            notes = notes,
            image = image
        )

        whenever(repository.upsertEvent(expectedEvent)).thenReturn(false)

        // when
        val result = useCase(
            eventType = eventType,
            nameContact = name,
            surnameContact = null,
            originalDate = originalDate,
            yearMatter = yearMatter,
            notes = notes,
            image = image
        )

        // then
        assertFalse(result)
        verify(repository).upsertEvent(expectedEvent)
    }
}
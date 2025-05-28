package com.example.domain.usecase

import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.models.event.SortTypeEvent
import com.example.domain.repository.local.EventRepository
import com.example.domain.useCase.calendar.event.UpsertEventUseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

class UpsertEventUseCaseTest {

    private val repository = mock<EventRepository>()
    private val useCase = UpsertEventUseCase(repository)

    @AfterEach
    fun tearDown(){
        Mockito.reset(repository)
    }

    @Test
    fun `invoke should call repository with correct Event and return true`() = runTest {
        // given
        val eventType = EventType.BIRTHDAY
        val sortTypeEvent = SortTypeEvent.FAMILY
        val name = "Alice"
        val surname = "Smith"
        val originalDate = LocalDate.of(1990, 5, 10)
        val yearMatter = true
        val notes = "Friend"
        val image = byteArrayOf(1, 2, 3)

        val expectedEvent = Event(
            id = 0,
            idContact = null,
            eventType = eventType,
            sortTypeEvent = sortTypeEvent,
            nameContact = name,
            surnameContact = surname,
            originalDate = originalDate,
            yearMatter = yearMatter,
            notes = notes,
            image = image
        )

        whenever(repository.upsertEvent(expectedEvent)).thenReturn(true)

        // when
        val result = useCase.invoke(
            idContact = null,
            eventType = eventType,
            sortTypeEvent = sortTypeEvent,
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
        val sortTypeEvent = SortTypeEvent.RELATIVE
        val name = "Bob"
        val originalDate = LocalDate.of(1985, 8, 20)
        val yearMatter = false
        val notes = null
        val image = null

        val expectedEvent = Event(
            id = 0,
            idContact = null,
            eventType = eventType,
            nameContact = name,
            surnameContact = null,
            originalDate = originalDate,
            yearMatter = yearMatter,
            notes = notes,
            image = image
        )

        whenever(repository.upsertEvent(expectedEvent)).thenReturn(false)

        // when
        val result = useCase.invoke(
            idContact = null,
            eventType = eventType,
            sortTypeEvent = sortTypeEvent,
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
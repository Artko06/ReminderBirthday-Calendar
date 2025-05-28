package com.example.domain.usecase

import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.repository.local.ContactAppRepository
import com.example.domain.useCase.calendar.event.ImportEventsFromContactsUseCase
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


class ImportEventUseCaseTest {

    private val repository = mock<ContactAppRepository>()
    private val useCase = ImportEventsFromContactsUseCase(repository)

    @AfterEach
    fun tearDown(){
        Mockito.reset(repository)
    }

    @Test
    fun `invoke should return list of imported events from repository`() = runTest {
        // given
        val mockEvents = listOf(
            Event(
                id = 1,
                idContact = null,
                eventType = EventType.BIRTHDAY,
                nameContact = "Alice",
                surnameContact = null,
                originalDate = LocalDate.of(2000, 5, 10),
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
                originalDate = LocalDate.of(1995, 7, 15),
                yearMatter = false,
                notes = "Work anniversary",
                image = null
            )
        )

        whenever(repository.importEvents()).thenReturn(mockEvents)

        // when
        val result = useCase()

        // then
        assertEquals(mockEvents, result)
        verify(repository).importEvents()
    }

    @Test
    fun `invoke should return empty list when no events are imported`() = runTest {
        // given
        whenever(repository.importEvents()).thenReturn(emptyList())

        // when
        val result = useCase()

        // then
        assertTrue(result.isEmpty())
        verify(repository).importEvents()
    }
}

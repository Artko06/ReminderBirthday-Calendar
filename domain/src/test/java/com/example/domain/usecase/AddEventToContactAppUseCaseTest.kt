package com.example.domain.usecase

import com.example.domain.models.event.EventType
import com.example.domain.repository.ContactAppRepository
import com.example.domain.useCase.calendar.event.AddEventToContactAppUseCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

class AddEventToContactAppUseCaseTest {

    private val repository = mock<ContactAppRepository>()
    private val useCase = AddEventToContactAppUseCase(repository)

    @Test
    fun `invoke should call repository and return true`() = runTest {
        // given
        val contactId = "123"
        val eventDate = LocalDate.of(2025, 4, 20)
        val eventType = EventType.BIRTHDAY

        whenever(repository.addEvent(contactId, eventDate.toString(), eventType)).thenReturn(true)

        // when
        val result = useCase(contactId, eventDate, eventType)

        // then
        assertTrue(result)
        verify(repository).addEvent(contactId, eventDate.toString(), eventType)
    }

    @Test
    fun `invoke should return false when repository returns false`() = runTest {
        // given
        val contactId = "456"
        val eventDate = LocalDate.of(2024, 12, 25)
        val eventType = EventType.ANNIVERSARY

        whenever(repository.addEvent(contactId, eventDate.toString(), eventType)).thenReturn(false)

        // when
        val result = useCase(contactId, eventDate, eventType)

        // then
        assertFalse(result)
        verify(repository).addEvent(contactId, eventDate.toString(), eventType)
    }
}
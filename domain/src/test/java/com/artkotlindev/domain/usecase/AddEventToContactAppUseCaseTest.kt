package com.artkotlindev.domain.usecase

import com.artkotlindev.domain.models.event.EventType
import com.artkotlindev.domain.repository.local.ContactAppRepository
import com.artkotlindev.domain.useCase.calendar.event.AddEventToContactAppUseCase
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

class AddEventToContactAppUseCaseTest {

    private val repository = mock<ContactAppRepository>()
    private val useCase = AddEventToContactAppUseCase(repository)

    @AfterEach
    fun tearDown(){
        Mockito.reset(repository)
    }

    @Test
    fun `invoke should call repository and return true`() = runTest {
        // given
        val contactId = "123"
        val eventDate = LocalDate.of(2025, 4, 20)
        val eventType = EventType.BIRTHDAY
        val yearMatter = true
        val customLabel = null

        whenever(repository.addEvent(contactId, eventDate.toString(), eventType, customLabel)).thenReturn(true)

        // when
        val result = useCase.invoke(contactId, eventDate, eventType, yearMatter, customLabel)

        // then
        assertTrue(result)
        verify(repository).addEvent(contactId, eventDate.toString(), eventType, customLabel)
    }

    @Test
    fun `invoke should return false when repository returns false`() = runTest {
        // given
        val contactId = "456"
        val eventDate = LocalDate.of(2024, 12, 25)
        val eventType = EventType.ANNIVERSARY
        val yearMatter = true
        val customLabel = null

        whenever(repository.addEvent(contactId, eventDate.toString(), eventType, customLabel)).thenReturn(false)

        // when
        val result = useCase(contactId, eventDate, eventType, yearMatter, customLabel)

        // then
        assertFalse(result)
        verify(repository).addEvent(contactId, eventDate.toString(), eventType, customLabel)
    }
}
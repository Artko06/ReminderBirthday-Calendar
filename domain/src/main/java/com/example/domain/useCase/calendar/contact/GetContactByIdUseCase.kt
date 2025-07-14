package com.example.domain.useCase.calendar.contact

import com.example.domain.models.event.ContactInfo
import com.example.domain.repository.local.ContactAppRepository

class GetContactByIdUseCase(private val repository: ContactAppRepository) {
    suspend operator fun invoke(contactId: String): ContactInfo {
        return repository.getContactInfoById(contactId = contactId) ?: ContactInfo(
            id = contactId,
            name = "",
            surname = "",
            phone = "",
            image = null
        )
    }
}
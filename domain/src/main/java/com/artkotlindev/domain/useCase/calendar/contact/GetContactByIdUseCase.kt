package com.artkotlindev.domain.useCase.calendar.contact

import com.artkotlindev.domain.models.event.ContactInfo
import com.artkotlindev.domain.repository.local.ContactAppRepository

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
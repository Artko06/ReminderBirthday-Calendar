package com.artkotlindev.domain.useCase.calendar.contact

import com.artkotlindev.domain.repository.local.ContactAppRepository

class LoadImageToContactUseCase(private val repository: ContactAppRepository) {
    suspend operator fun invoke(contactId: String): ByteArray? {
        return repository.loadImageToContact(contactId = contactId)
    }
}
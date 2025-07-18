package com.artkotlindev.domain.useCase.calendar.contact

import com.artkotlindev.domain.models.event.ContactInfo
import com.artkotlindev.domain.repository.local.ContactAppRepository

class ImportContactsUseCase(private val repository: ContactAppRepository) {
    suspend operator fun invoke(): List<ContactInfo> = repository.importContacts()
}

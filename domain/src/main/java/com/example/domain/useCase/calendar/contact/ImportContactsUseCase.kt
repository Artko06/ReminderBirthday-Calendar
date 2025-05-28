package com.example.domain.useCase.calendar.contact

import com.example.domain.models.event.ContactInfo
import com.example.domain.repository.local.ContactAppRepository

class ImportContactsUseCase(private val repository: ContactAppRepository) {
    suspend operator fun invoke(): List<ContactInfo> = repository.importContacts()
}

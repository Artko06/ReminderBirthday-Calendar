package com.example.domain.useCase.calendar.contact

import com.example.domain.models.event.ContactInfo
import com.example.domain.repository.ContactImportRepository

class ImportContactsUseCase(private val repository: ContactImportRepository) {
    suspend operator fun invoke(): List<ContactInfo> = repository.importContacts()
}

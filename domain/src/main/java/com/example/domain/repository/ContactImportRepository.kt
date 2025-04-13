package com.example.domain.repository

import com.example.domain.models.event.ContactInfo

interface ContactImportRepository {
    suspend fun importContacts(): List<ContactInfo>
//    suspend fun saveEvents(events: List<ImportedEvent>)
}
package com.example.domain.repository

import com.example.domain.models.event.ContactInfo
import com.example.domain.models.event.Event

interface ContactImportRepository {
    suspend fun importContacts(): List<ContactInfo>
    suspend fun importEvents() : List<Event>
}
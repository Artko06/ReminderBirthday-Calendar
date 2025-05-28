package com.example.domain.repository.local

import com.example.domain.models.event.ContactInfo
import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType

interface ContactAppRepository {
    suspend fun importContacts(): List<ContactInfo>
    suspend fun importEvents() : List<Event>
    suspend fun addEvent(
        contactId: String,
        eventDate: String,
        eventType: EventType
    ): Boolean
}
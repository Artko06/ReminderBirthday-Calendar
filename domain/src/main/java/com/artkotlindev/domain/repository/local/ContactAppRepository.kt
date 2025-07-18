package com.artkotlindev.domain.repository.local

import com.artkotlindev.domain.models.event.ContactInfo
import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.models.event.EventType

interface ContactAppRepository {
    suspend fun importContacts(): List<ContactInfo>
    suspend fun importEvents() : List<Event>
    suspend fun addEvent(
        contactId: String,
        eventDate: String,
        eventType: EventType,
        customLabel: String?
    ): Boolean
    suspend fun getContactInfoById(contactId: String): ContactInfo?
}
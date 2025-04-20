package com.example.domain.repository

import com.example.domain.models.event.Event

interface GoogleClientRepository {
    suspend fun signIn(): Boolean
    suspend fun signOut()
    fun isSignedIn(): Boolean
    suspend fun uploadEventsToRemote(): Boolean
    suspend fun getEventsFromRemote(): List<Event>
    suspend fun getTimeFromRemote(): String?
}
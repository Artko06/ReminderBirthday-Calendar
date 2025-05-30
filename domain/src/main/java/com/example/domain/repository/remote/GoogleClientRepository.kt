package com.example.domain.repository.remote

import com.example.domain.models.event.Event

interface GoogleClientRepository {
    suspend fun signIn(): Boolean
    suspend fun signOut()
    fun isSignedIn(): Boolean
    fun emailSignInUser(): String?
    suspend fun uploadEventsToRemote(): Boolean
    suspend fun getEventsFromRemote(): List<Event>
    suspend fun getTimeFromRemote(): String?
}
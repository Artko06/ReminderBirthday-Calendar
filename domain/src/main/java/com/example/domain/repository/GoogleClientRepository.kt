package com.example.domain.repository

interface GoogleClientRepository {
    suspend fun signIn(): Boolean
    suspend fun signOut()
    fun isSignedIn(): Boolean
}
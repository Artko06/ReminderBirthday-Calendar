package com.example.data.repository

import com.example.data.local.util.google.GoogleAuthClient
import com.example.domain.repository.GoogleClientRepository
import javax.inject.Inject

class GoogleClientRepositoryImpl @Inject constructor(
    private val googleAuthClient: GoogleAuthClient
): GoogleClientRepository {
    override suspend fun signIn(): Boolean {
        return googleAuthClient.signIn()
    }

    override suspend fun signOut() {
        googleAuthClient.signOut()
    }

    override fun isSignedIn(): Boolean {
        return googleAuthClient.isSignedIn()
    }
}
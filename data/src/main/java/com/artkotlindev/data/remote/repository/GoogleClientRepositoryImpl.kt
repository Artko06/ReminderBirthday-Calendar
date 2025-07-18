package com.artkotlindev.data.remote.repository

import com.artkotlindev.data.local.entity.event.toDomain
import com.artkotlindev.data.remote.util.google.GoogleAuthClient
import com.artkotlindev.data.remote.util.google.GoogleDriveClient
import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.repository.remote.GoogleClientRepository
import javax.inject.Inject

class GoogleClientRepositoryImpl @Inject constructor(
    private val googleAuthClient: GoogleAuthClient,
    private val googleDriveClient: GoogleDriveClient
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

    override fun emailSignInUser(): String? {
        return googleAuthClient.emailSignInUser()
    }

    override suspend fun uploadEventsToRemote(): Boolean {
        if (!googleAuthClient.isSignedIn()) {
            println("User not signed in")
            return false
        }

        return googleDriveClient.uploadEventsToRemote()
    }

    override suspend fun getEventsFromRemote(): List<Event> {
        return googleDriveClient.getEventsFromRemote().map { it.toDomain() }
    }

    override suspend fun getTimeFromRemote(): String? {
        return googleDriveClient.getTimeFromRemote()
    }
}
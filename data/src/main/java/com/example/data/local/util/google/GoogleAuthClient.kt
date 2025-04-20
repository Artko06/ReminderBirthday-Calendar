package com.example.data.local.util.google

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthClient(
    private val context: Context
) {
    private val tag = "GoogleAuthClient: "

    private val credentialManager = CredentialManager.create(context = context)
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun isSignedIn(): Boolean {
        return if (firebaseAuth.currentUser != null) {
            println(tag + "user already signIn")
            true
        } else false
    }

    suspend fun signIn(): Boolean {
        if (isSignedIn()) {
            return true
        }

        try {
            val result = buildCredentialRequest()
            return handleSignIn(result)

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e

            println(tag + "SignIn error: ${e.message}")
            return false
        }
    }

    suspend fun signOut() {
        credentialManager.clearCredentialState(request = ClearCredentialStateRequest())

        firebaseAuth.signOut()
    }

    private suspend fun handleSignIn(response: GetCredentialResponse): Boolean {
        val credential = response.credential

        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                println(tag + "name: ${tokenCredential.displayName}")
                println(tag + "email: ${tokenCredential.id}")
                println(tag + "image: ${tokenCredential.profilePictureUri}")

                val authCredential = GoogleAuthProvider.getCredential(
                    tokenCredential.idToken, null
                )
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()

                return authResult.user != null

            } catch (e: GoogleIdTokenParsingException) {
                println(tag + "GoogleIdTokenParsingException: ${e.message}")
                return false
            }

        } else {
            println(tag + "credential is not GOOGLE_ID_TOKEN_CREDENTIAL")
            return false
        }
    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(
                serverClientId = WEB_CLIENT_ID
            )
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return credentialManager.getCredential(
            context = context,
            request = request
        )
    }
}
package com.artkotlindev.domain.useCase.google

import com.artkotlindev.domain.repository.remote.GoogleClientRepository

class GoogleIsSignInUseCase(
    private val repository: GoogleClientRepository
) {
    operator fun invoke(): Boolean = repository.isSignedIn()
}
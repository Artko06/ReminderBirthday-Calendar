package com.example.domain.useCase.google

import com.example.domain.repository.GoogleClientRepository

class GoogleIsSignInUseCase(
    private val repository: GoogleClientRepository
) {
    operator fun invoke(): Boolean = repository.isSignedIn()
}
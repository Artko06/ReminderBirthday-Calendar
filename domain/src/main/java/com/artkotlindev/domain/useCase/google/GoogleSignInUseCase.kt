package com.artkotlindev.domain.useCase.google

import com.artkotlindev.domain.repository.remote.GoogleClientRepository

class GoogleSignInUseCase(
    private val repository: GoogleClientRepository
) {
    suspend operator fun invoke(): Boolean = repository.signIn()
}
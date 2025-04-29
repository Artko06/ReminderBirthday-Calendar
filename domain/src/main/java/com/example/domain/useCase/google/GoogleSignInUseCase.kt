package com.example.domain.useCase.google

import com.example.domain.repository.GoogleClientRepository

class GoogleSignInUseCase(
    private val repository: GoogleClientRepository
) {
    suspend operator fun invoke(): Boolean = repository.signIn()
}
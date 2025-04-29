package com.example.domain.useCase.google

import com.example.domain.repository.GoogleClientRepository

class GoogleSignOutUseCase(
    private val repository: GoogleClientRepository
) {
    suspend operator fun invoke() = repository.signOut()
}
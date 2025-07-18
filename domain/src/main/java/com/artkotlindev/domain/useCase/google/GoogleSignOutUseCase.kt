package com.artkotlindev.domain.useCase.google

import com.artkotlindev.domain.repository.remote.GoogleClientRepository

class GoogleSignOutUseCase(
    private val repository: GoogleClientRepository
) {
    suspend operator fun invoke() = repository.signOut()
}
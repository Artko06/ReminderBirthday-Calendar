package com.example.domain.useCase.google

import com.example.domain.repository.GoogleClientRepository

class GetAuthGoogleEmailUseCase(
    private val repository: GoogleClientRepository
) {
    operator fun invoke(): String? = repository.emailSignInUser()
}
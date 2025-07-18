package com.artkotlindev.domain.useCase.google

import com.artkotlindev.domain.repository.remote.GoogleClientRepository

class GetAuthGoogleEmailUseCase(
    private val repository: GoogleClientRepository
) {
    operator fun invoke(): String? = repository.emailSignInUser()
}
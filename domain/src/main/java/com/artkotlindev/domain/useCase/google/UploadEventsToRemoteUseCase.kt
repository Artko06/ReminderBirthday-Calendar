package com.artkotlindev.domain.useCase.google

import com.artkotlindev.domain.repository.remote.GoogleClientRepository

class UploadEventsToRemoteUseCase(
    private val repository: GoogleClientRepository
) {
    suspend operator fun invoke(): Boolean = repository.uploadEventsToRemote()
}
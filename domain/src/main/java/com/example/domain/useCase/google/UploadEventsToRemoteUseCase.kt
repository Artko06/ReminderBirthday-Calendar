package com.example.domain.useCase.google

import com.example.domain.repository.GoogleClientRepository

class UploadEventsToRemoteUseCase(
    private val repository: GoogleClientRepository
) {
    suspend operator fun invoke(): Boolean = repository.uploadEventsToRemote()
}
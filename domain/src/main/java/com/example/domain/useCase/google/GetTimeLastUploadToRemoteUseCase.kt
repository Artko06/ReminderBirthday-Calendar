package com.example.domain.useCase.google

import com.example.domain.repository.remote.GoogleClientRepository

class GetTimeLastUploadToRemoteUseCase(
    private val repository: GoogleClientRepository
) {
    suspend operator fun invoke(): String? = repository.getTimeFromRemote()
}
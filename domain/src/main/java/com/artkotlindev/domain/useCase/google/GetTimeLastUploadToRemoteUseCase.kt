package com.artkotlindev.domain.useCase.google

import com.artkotlindev.domain.repository.remote.GoogleClientRepository

class GetTimeLastUploadToRemoteUseCase(
    private val repository: GoogleClientRepository
) {
    suspend operator fun invoke(): String? = repository.getTimeFromRemote()
}
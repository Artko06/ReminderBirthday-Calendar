package com.artkotlindev.domain.useCase.exportFile

import com.artkotlindev.domain.repository.local.ExportFileRepository

class ExportEventsToCsvToExternalDirUseCase(
    private val repository: ExportFileRepository
) {
    suspend operator fun invoke(): Boolean =
        repository.exportEventsToCsvToExternalDir()
}
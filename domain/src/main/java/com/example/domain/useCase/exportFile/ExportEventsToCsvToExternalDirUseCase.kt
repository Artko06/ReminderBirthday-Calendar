package com.example.domain.useCase.exportFile

import com.example.domain.repository.local.ExportFileRepository

class ExportEventsToCsvToExternalDirUseCase(
    private val repository: ExportFileRepository
) {
    suspend operator fun invoke(): Boolean =
        repository.exportEventsToCsvToExternalDir()
}
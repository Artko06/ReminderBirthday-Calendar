package com.example.domain.useCase.exportFile

import com.example.domain.repository.ExportFileRepository

class ExportEventsToJsonToExternalDirUseCase(
    private val repository: ExportFileRepository
) {
    suspend operator fun invoke(): Boolean =
        repository.exportEventsToJsonToExternalDir()
}
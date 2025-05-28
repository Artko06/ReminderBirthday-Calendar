package com.example.domain.repository.local

interface ExportFileRepository {
    suspend fun exportEventsToJsonToExternalDir(): Boolean
    suspend fun exportEventsToCsvToExternalDir(): Boolean
}
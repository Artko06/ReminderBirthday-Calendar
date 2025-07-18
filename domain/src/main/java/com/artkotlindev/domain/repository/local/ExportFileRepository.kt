package com.artkotlindev.domain.repository.local

interface ExportFileRepository {
    suspend fun exportEventsToJsonToExternalDir(): Boolean
    suspend fun exportEventsToCsvToExternalDir(): Boolean
}
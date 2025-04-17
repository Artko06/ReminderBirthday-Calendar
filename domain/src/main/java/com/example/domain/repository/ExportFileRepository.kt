package com.example.domain.repository

interface ExportFileRepository {
    suspend fun exportEventsToJsonToExternalDir()
    suspend fun exportEventsToCsvToExternalDir()
}
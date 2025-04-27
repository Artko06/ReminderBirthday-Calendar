package com.example.domain.repository

interface ExportFileRepository {
    suspend fun exportEventsToJsonToExternalDir(): Boolean
    suspend fun exportEventsToCsvToExternalDir(): Boolean
}
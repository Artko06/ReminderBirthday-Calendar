package com.example.data.repository

import android.content.Context
import com.example.data.local.entity.settings.event.toDomain
import com.example.data.local.util.serialization.toEventEntity
import com.example.domain.models.event.Event
import com.example.domain.repository.ImportFileRepository
import com.example.serializationmodule.deserialization.Deserialization
import javax.inject.Inject


class ImportFileRepositoryImpl @Inject constructor(
    private val context: Context
): ImportFileRepository {
    override suspend fun importEventsFromJson(): List<Event> {
        return try {
            Deserialization.importEventsFromJsonFromExternalDir(context = context).map { it.toEventEntity().toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun importEventsFromCsv(): List<Event> {
        return try {
            Deserialization.importEventsFromCsvFromExternalDir(context = context).map { it.toEventEntity().toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
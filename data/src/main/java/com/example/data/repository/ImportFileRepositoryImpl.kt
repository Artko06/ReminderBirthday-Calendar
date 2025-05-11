package com.example.data.repository

import android.content.Context
import androidx.core.net.toUri
import com.example.data.local.entity.settings.event.toDomain
import com.example.data.local.util.serialization.Deserialization
import com.example.data.local.util.serialization.toEventEntity
import com.example.domain.models.event.Event
import com.example.domain.repository.ImportFileRepository
import javax.inject.Inject


class ImportFileRepositoryImpl @Inject constructor(
    private val context: Context
): ImportFileRepository {
    override suspend fun importEventsFromJson(strUri: String): List<Event> {
        return try {
            Deserialization.importEventsFromJson(context = context, uri = strUri.toUri()).map { it.toEventEntity().toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun importEventsFromCsv(strUri: String): List<Event> {
        return try {
            Deserialization.importEventsFromCsv(context = context, uri = strUri.toUri()).map { it.toEventEntity().toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
package com.artkotlindev.data.local.repository

import android.content.Context
import androidx.core.net.toUri
import com.artkotlindev.data.local.entity.event.toDomain
import com.artkotlindev.data.local.util.serialization.Deserialization
import com.artkotlindev.data.local.util.serialization.toEventEntity
import com.artkotlindev.domain.models.event.Event
import com.artkotlindev.domain.repository.local.ImportFileRepository
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
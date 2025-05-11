package com.example.data.repository

import android.content.Context
import com.example.data.local.roomDb.dao.EventDao
import com.example.data.local.util.serialization.Serialization
import com.example.data.local.util.serialization.toEventSerializable
import com.example.domain.repository.ExportFileRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ExportFileRepositoryImpl @Inject constructor(
    private val context: Context,
    private val eventDao: EventDao
) : ExportFileRepository {
    override suspend fun exportEventsToJsonToExternalDir(): Boolean {
        return try {
            val events = eventDao.getAllEvents().first().map { it.toEventSerializable() }

            Serialization.exportEventsToJsonToExternalDir(context = context, events = events)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun exportEventsToCsvToExternalDir(): Boolean {
        return try {
            val events = eventDao.getAllEvents().first().map { it.toEventSerializable() }

            Serialization.exportEventsToCsvToExternalDir(context = context, events = events)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
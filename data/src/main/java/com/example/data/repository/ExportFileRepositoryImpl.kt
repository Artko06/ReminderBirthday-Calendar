package com.example.data.repository

import android.content.Context
import com.example.data.local.roomDb.dao.EventDao
import com.example.data.local.util.serialization.toEventSerializable
import com.example.domain.repository.ExportFileRepository
import com.example.serializationmodule.serialization.Serialization
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ExportFileRepositoryImpl @Inject constructor(
    private val context: Context,
    private val eventDao: EventDao
) : ExportFileRepository {
    override suspend fun exportEventsToJsonToExternalDir() {
        try {
            val events = eventDao.getAllEvents().first().map { it.toEventSerializable() }

            Serialization.exportEventsToJsonToExternalDir(context = context, events = events)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun exportEventsToCsvToExternalDir() {
        try {
            val events = eventDao.getAllEvents().first().map { it.toEventSerializable() }

            Serialization.exportEventsToCsvToExternalDir(context = context, events = events)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
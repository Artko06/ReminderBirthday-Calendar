package com.example.data.repository

import android.content.Context
import android.util.Base64
import com.example.data.local.roomDb.dao.EventDao
import com.example.domain.repository.ExportFileRepository
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

class ExportFileRepositoryImpl @Inject constructor(
    private val context: Context,
    private val eventDao: EventDao
) : ExportFileRepository {
    override suspend fun exportEventsToCsvToExternalDir() {
        val externalFile = File(context.getExternalFilesDir(null), "exported_events.csv")
        val events = eventDao.getAllEvents().first()

        val csvBuilder = StringBuilder()

        csvBuilder.appendLine(
                    "id," +
                    "eventType," +
                    "nameContact," +
                    "surnameContact," +
                    "originalDate," +
                    "yearMatter," +
                    "nextDate," +
                    "notes," +
                    "image"
        )

        for (event in events) {
            val base64Image = event.image?.let { Base64.encodeToString(it, Base64.NO_WRAP) } ?: ""

            val line = listOf(
                event.id,
                event.eventType,
                event.nameContact,
                event.surnameContact ?: "",
                event.originalDate,
                event.yearMatter,
                event.nextDate,
                event.notes ?: "",
                base64Image
            ).joinToString(separator = ",")

            csvBuilder.appendLine(line)
        }

        externalFile.writeText(csvBuilder.toString())
    }

    override suspend fun exportEventsToJsonToExternalDir() {
        val externalFile = File(context.getExternalFilesDir(null), "exported_events.json")
        val events = eventDao.getAllEvents().first()
        val json = Json { prettyPrint = true }.encodeToString(events)

        externalFile.writeText(json)
    }
}
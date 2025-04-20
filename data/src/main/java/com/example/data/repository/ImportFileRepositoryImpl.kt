package com.example.data.repository

import android.content.Context
import android.util.Base64
import com.example.data.local.entity.settings.event.EventEntity
import com.example.data.local.entity.settings.event.toDomain
import com.example.domain.models.event.Event
import com.example.domain.repository.ImportFileRepository
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject


class ImportFileRepositoryImpl @Inject constructor(
    private val context: Context
): ImportFileRepository {
    override suspend fun importEventsFromCsv(): List<Event> {
        return try {
            val file = File(context.getExternalFilesDir(null), "exported_events.csv")
            val lines = file.readLines()
            if (lines.isEmpty()) return emptyList()

            val events = mutableListOf<Event>()

            for (line in lines.drop(1)) { // Skip title
                val tokens = line.split(",")

                if (tokens.size < 9) continue // Skip empty strings

                val id = tokens[0]
                val eventType = tokens[1]
                val nameContact = tokens[2]
                val surnameContact = tokens[3]
                val originalDate = tokens[4]
                val yearMatter = tokens[5].toBoolean()
                val nextDate = tokens[6]
                val notes = tokens[7]
                val imageBase64 = tokens[8]
                val imageBytes = if (imageBase64.isNotBlank()) Base64.decode(imageBase64, Base64.NO_WRAP) else null

                events.add(
                    EventEntity(
                        id = id.toInt(),
                        eventType = eventType,
                        nameContact = nameContact,
                        surnameContact = surnameContact,
                        originalDate = originalDate,
                        yearMatter = yearMatter,
                        nextDate = nextDate,
                        notes = notes,
                        image = imageBytes
                    ).toDomain()
                )
            }
            println(events)
            events
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun importEventsFromJson(): List<Event> {
        return try {
            val file = File(context.getExternalFilesDir(null), "exported_events.json")
            val jsonString = file.readText()
            val listEvents = Json.decodeFromString(jsonString) as List<EventEntity>
            listEvents.map { it.toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
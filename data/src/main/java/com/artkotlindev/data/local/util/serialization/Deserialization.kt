package com.artkotlindev.data.local.util.serialization

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import java.io.FileNotFoundException

object Deserialization {
    fun getFileExtensionFromUri(context: Context, uri: Uri): String? {
        val contentResolver = context.contentResolver
        val returnCursor = contentResolver.query(uri, null, null, null, null)
        returnCursor?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex != -1) {
                val fileName = cursor.getString(nameIndex)
                return fileName.substringAfterLast('.', "")
            }
        }
        return null
    }

    fun importEventsFromJson(context: Context, uri: Uri): List<EventSerializable> {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: throw FileNotFoundException("Unable to open InputStream for Uri")

            val extension = getFileExtensionFromUri(context, uri)
            Log.d("TYPE", extension ?: "null")
            if (extension != "json") {
                throw IllegalArgumentException("Invalid file extension: expected .json")
            }

            val result = mutableListOf<EventSerializable>()
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val eventsStr = jsonString
                .trim()
                .removeSurrounding("[", "]")
                .split(Regex("\\},\\s*\\{"))
                .map { it.trim().removePrefix("{").removeSuffix("}") }

            eventsStr.forEach { item ->
                val linesEvent = item.split(",\n").map { it.trim() }
                val mapEvent = mutableMapOf<String, String>()

                for(line in linesEvent) {
                    mapEvent[line.substringBefore(":").removeSurrounding("\"")] =
                        line.substringAfter(":").trim().removeSurrounding("\"")
                }

                println(mapEvent)

                val imageBytes = if (mapEvent["image"].isNullOrEmpty()) null
                else Base64.decode(mapEvent["image"], Base64.NO_WRAP)

                result.add(
                    EventSerializable(
                        id = mapEvent["id"]!!.toLong(),
                        idContact = mapEvent["idContact"],
                        eventType = mapEvent["eventType"]!!,
                        sortTypeEvent = mapEvent["sortTypeEvent"]!!,
                        nameContact = mapEvent["nameContact"]!!,
                        surnameContact = mapEvent["surnameContact"],
                        originalDate = mapEvent["originalDate"]!!,
                        yearMatter = mapEvent["yearMatter"]!!.toBoolean(),
                        notes = if (mapEvent["notes"] != null) {
                            mapEvent["notes"]!!.take(500)
                        } else { mapEvent["notes"] },
                        image = imageBytes
                    )
                )
            }

            result
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
        return emptyList()
    }

    fun importEventsFromCsv(context: Context, uri: Uri): List<EventSerializable> {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: throw FileNotFoundException("Unable to open InputStream for Uri")

            val extension = getFileExtensionFromUri(context, uri)
            if (extension != "csv") {
                throw IllegalArgumentException("Invalid file extension: expected .csv")
            }

            val lines = inputStream.bufferedReader().readLines()
            if (lines.isEmpty()) return emptyList()

            val events = mutableListOf<EventSerializable>()

            for (line in lines.drop(1)) { // Skip title
                val tokens = line.split(",")

                if (tokens.size < 9) continue // Skip empty strings

                val id = tokens[0]
                val idContact = tokens[1]
                val eventType = tokens[2]
                val sortType = tokens[3]
                val nameContact = tokens[4]
                val surnameContact = tokens[5]
                val originalDate = tokens[6]
                val yearMatter = tokens[7].toBoolean()
                val notes = tokens[8].take(500)
                val imageBase64 = tokens[9]
                val imageBytes = if (imageBase64.isNotBlank()) Base64.decode(imageBase64, Base64.NO_WRAP) else null

                events.add(
                    EventSerializable(
                        id = id.toLong(),
                        idContact = idContact,
                        eventType = eventType,
                        sortTypeEvent = sortType,
                        nameContact = nameContact,
                        surnameContact = surnameContact,
                        originalDate = originalDate,
                        yearMatter = yearMatter,
                        notes = notes,
                        image = imageBytes
                    )
                )
            }

            events
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

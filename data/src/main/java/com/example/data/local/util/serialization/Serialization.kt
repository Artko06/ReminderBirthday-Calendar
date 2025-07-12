package com.example.data.local.util.serialization

import android.content.Context
import java.io.File

object Serialization {
    fun exportEventsToJsonToExternalDir(context: Context, events: List<EventSerializable>) {
        val externalFile = File(context.getExternalFilesDir(null), "exported_events.json")

        val strBuilder = StringBuilder("[\n")

        events.forEachIndexed { index, event ->
            strBuilder.append(" ".repeat(2) + "{\n")
            strBuilder.append(" ".repeat(4) + "\"id\": ${event.id},\n")
            strBuilder.append(" ".repeat(4) + "\"idContact\": ${event.idContact},\n")
            strBuilder.append(" ".repeat(4) + "\"eventType\": \"${event.eventType}\",\n")
            strBuilder.append(" ".repeat(4) + "\"sortTypeEvent\": \"${event.sortTypeEvent}\",\n")
            strBuilder.append(" ".repeat(4) + "\"nameContact\": \"${event.nameContact}\",\n")
            strBuilder.append(" ".repeat(4) + "\"surnameContact\": \"${event.surnameContact ?: ""}\",\n")
            strBuilder.append(" ".repeat(4) + "\"originalDate\": \"${event.originalDate}\",\n")
            strBuilder.append(" ".repeat(4) + "\"yearMatter\": ${event.yearMatter},\n")
            strBuilder.append(" ".repeat(4) + "\"notes\": \"${event.notes ?: ""}\",\n")

            val image = ""
            // val imageBase64 = event.image?.let { Base64.encodeToString(it, Base64.NO_WRAP) } ?: ""
            // without image
            strBuilder.append(" ".repeat(4) + "\"image\": \"${image}\"\n")
            strBuilder.append(" ".repeat(2) + "}")
            if (index != events.lastIndex) strBuilder.append(",")
            strBuilder.append("\n")
        }
        strBuilder.append("]")

        externalFile.writeText(strBuilder.toString())
    }

    fun exportEventsToCsvToExternalDir(context: Context, events: List<EventSerializable>) {
        val externalFile = File(context.getExternalFilesDir(null), "exported_events.csv")

        val csvBuilder = StringBuilder()

        csvBuilder.appendLine(
                    "id," +
                    "idContact," +
                    "eventType," +
                    "sortTypeEvent," +
                    "nameContact," +
                    "surnameContact," +
                    "originalDate," +
                    "yearMatter," +
                    "notes," +
                    "image"
        )

        for (event in events) {
            val image = ""
            // val base64Image = event.image?.let { Base64.encodeToString(it, Base64.NO_WRAP) } ?: ""
            // without image

            val line = listOf(
                event.id,
                event.idContact ?: "",
                event.eventType,
                event.sortTypeEvent,
                event.nameContact,
                event.surnameContact ?: "",
                event.originalDate,
                event.yearMatter,
                event.notes ?: "",
                image
            ).joinToString(separator = ",")

            csvBuilder.appendLine(line)
        }

        externalFile.writeText(csvBuilder.toString())
    }
}
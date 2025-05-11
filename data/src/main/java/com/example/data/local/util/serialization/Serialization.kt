package com.example.data.local.util.serialization

import android.content.Context
import android.util.Base64
import java.io.File

object Serialization {
    fun exportEventsToJsonToExternalDir(context: Context, events: List<EventSerializable>) {
        val externalFile = File(context.getExternalFilesDir(null), "exported_events.json")

        val strBuilder = StringBuilder("[\n")

        events.forEachIndexed { index, event ->
            strBuilder.append(" ".repeat(2) + "{\n")
            strBuilder.append(" ".repeat(4) + "\"id\": ${event.id},\n")
            strBuilder.append(" ".repeat(4) + "\"eventType\": \"${event.eventType}\",\n")
            strBuilder.append(" ".repeat(4) + "\"sortTypeEvent\": \"${event.sortTypeEvent}\",\n")
            strBuilder.append(" ".repeat(4) + "\"nameContact\": \"${event.nameContact}\",\n")
            strBuilder.append(" ".repeat(4) + "\"surnameContact\": \"${event.surnameContact ?: ""}\",\n")
            strBuilder.append(" ".repeat(4) + "\"originalDate\": \"${event.originalDate}\",\n")
            strBuilder.append(" ".repeat(4) + "\"yearMatter\": ${event.yearMatter},\n")
            strBuilder.append(" ".repeat(4) + "\"notes\": \"${event.notes ?: ""}\",\n")
            val imageBase64 = event.image?.let { Base64.encodeToString(it, Base64.NO_WRAP) } ?: ""
            strBuilder.append(" ".repeat(4) + "\"image\": \"${imageBase64}\"\n")
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
            val base64Image = event.image?.let { Base64.encodeToString(it, Base64.NO_WRAP) } ?: ""

            val line = listOf(
                event.id,
                event.eventType,
                event.sortTypeEvent,
                event.nameContact,
                event.surnameContact ?: "",
                event.originalDate,
                event.yearMatter,
                event.notes ?: "",
                base64Image
            ).joinToString(separator = ",")

            csvBuilder.appendLine(line)
        }

        externalFile.writeText(csvBuilder.toString())
    }
}
package com.example.data.local.util.google

import com.example.data.local.entity.settings.event.EventEntity
import com.example.data.local.roomDb.dao.EventDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.ZoneId

class GoogleDriveClient(
    private val eventDao: EventDao
) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun uploadEventsToRemote(): Boolean {
        return try {
            val events = eventDao.getAllEvents().first().map { it.toFirestoreMap() }

            val userEmail =
                firebaseAuth.currentUser?.email ?: throw Exception("User not authenticated")
            val dateTime = LocalDateTime.now()

            val day = if(dateTime.dayOfMonth < 10) "0" + dateTime.dayOfMonth.toString()
                else dateTime.dayOfMonth.toString()

            val month = if(dateTime.month.value < 10) "0" + dateTime.month.value.toString()
                else dateTime.month.value.toString()

            val year = dateTime.year

            val hour = dateTime.hour

            val minute = if(dateTime.minute < 10) "0" + dateTime.minute.toString()
                else dateTime.minute.toString()

            firestore.collection(userEmail)
                .document("Bdays - $day.$month.$year " +
                        "($hour:$minute)")
                .set(
                    mapOf(
                        "time" to FieldValue.serverTimestamp(),
                        "events" to events
                    )
                )
                .await()

            println("Uploaded successfully")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            println("Upload failed: ${e.message}")
            false
        }
    }

    suspend fun getEventsFromRemote(): List<EventEntity> {
        val userEmail =
            firebaseAuth.currentUser?.email ?: throw Exception("User not authenticated")

        return try {
            val query= firestore.collection(userEmail)
                .orderBy("time", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            println("Query is empty? ${query.isEmpty}")
            if (query.isEmpty) return emptyList()

            val eventsData = query.documents[0].get("events") as? List<Map<String, Any?>>
                ?: throw Exception("Invalid events format")

            eventsData.map { firestoreMapToEventEntity(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getTimeFromRemote(): String? {
        val userEmail =
            firebaseAuth.currentUser?.email ?: throw Exception("User not authenticated")

        return try {
            val query= firestore.collection(userEmail)
                .orderBy("time", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            println("Query is empty? ${query.isEmpty}")
            if (query.isEmpty) return null

            val dateTime = query.documents[0].getTimestamp("time").toLocalDateTime()
            if (dateTime == null) return null

            val day = if(dateTime.dayOfMonth < 10) "0" + dateTime.dayOfMonth.toString()
                else dateTime.dayOfMonth.toString()

            val month = if(dateTime.month.value < 10) "0" + dateTime.month.value.toString()
                else dateTime.month.value.toString()

            val year = dateTime.year

            val hour = dateTime.hour

            val minute = if(dateTime.minute < 10) "0" + dateTime.minute.toString()
                else dateTime.minute.toString()

            val seconds = if(dateTime.second < 10) "0" + dateTime.second.toString()
                else dateTime.second.toString()

            "$day.$month.$year " +
            "($hour:$minute:$seconds)"
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun com.google.firebase.Timestamp?.toLocalDateTime(): LocalDateTime? {
        return this?.toDate()
            ?.toInstant()
            ?.atZone(ZoneId.systemDefault())
            ?.toLocalDateTime()
    }

    private fun EventEntity.toFirestoreMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "eventType" to eventType,
            "sortTypeEvent" to sortType,
            "nameContact" to nameContact,
            "surnameContact" to surnameContact,
            "originalDate" to originalDate,
            "yearMatter" to yearMatter,
            "notes" to notes,
        )
    }

    private fun firestoreMapToEventEntity(event: Map<String, Any?>): EventEntity {
        return EventEntity(
            id = (event["id"] as Long),
            idContact = event["idContact"] as? String,
            eventType = event["eventType"] as String,
            sortType = event["sortTypeEvent"] as String,
            nameContact = event["nameContact"] as String,
            surnameContact = event["surnameContact"] as? String,
            originalDate = event["originalDate"] as String,
            yearMatter = event["yearMatter"] as Boolean,
            notes = event["notes"] as? String,
            image = null
        )
    }
}
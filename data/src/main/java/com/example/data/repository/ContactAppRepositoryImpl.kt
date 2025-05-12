package com.example.data.repository

import android.Manifest
import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.ContentUris
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.provider.ContactsContract
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.database.getStringOrNull
import com.example.data.local.util.image.bitmapToByteArray
import com.example.data.local.util.image.getBitmapSquareSize
import com.example.domain.models.event.ContactInfo
import com.example.domain.models.event.Event
import com.example.domain.models.event.EventType
import com.example.domain.models.event.ImportedEvent
import com.example.domain.repository.ContactAppRepository
import java.time.LocalDate

class ContactAppRepositoryImpl(
    private val contentResolver: ContentResolver
) : ContactAppRepository {
    @RequiresPermission(Manifest.permission.READ_CONTACTS)
    override suspend fun importContacts(): List<ContactInfo> {
        return getContacts(resolver = contentResolver)
    }

    @RequiresPermission(Manifest.permission.READ_CONTACTS)
    override suspend fun importEvents(): List<Event> {
        return getEventsFromContacts(resolver = contentResolver)
    }

    @RequiresPermission(allOf = [Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS])
    override suspend fun addEvent(
        contactId: String,
        eventDate: String,
        eventType: EventType
    ): Boolean {
        return addEventToContact(
            resolver = contentResolver,
            contactId = contactId,
            eventDate = eventDate,
            eventType = eventType
        )
    }

    @RequiresPermission(allOf = [Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS])
    private fun addEventToContact(
        resolver: ContentResolver,
        contactId: String,
        eventDate: String,
        eventType: EventType
    ): Boolean {
        val operations = ArrayList<ContentProviderOperation>()

        // Get rawContactId by contactId
        val rawContactIdCursor = resolver.query(
            ContactsContract.RawContacts.CONTENT_URI,
            arrayOf(ContactsContract.RawContacts._ID),
            "${ContactsContract.RawContacts.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )

        var rawContactId: Long? = null
        if (rawContactIdCursor != null && rawContactIdCursor.moveToFirst()) {
            rawContactId = rawContactIdCursor.getLong(0)
            rawContactIdCursor.close()
        }

        if (rawContactId == null) return false

        // Type Android event
        val androidEventType = when (eventType) {
            EventType.BIRTHDAY -> ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY
            EventType.ANNIVERSARY -> ContactsContract.CommonDataKinds.Event.TYPE_ANNIVERSARY
            EventType.OTHER -> ContactsContract.CommonDataKinds.Event.TYPE_OTHER
        }

        val builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            .withValue(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
            )
            .withValue(ContactsContract.CommonDataKinds.Event.TYPE, androidEventType)
            .withValue(ContactsContract.CommonDataKinds.Event.START_DATE, eventDate)

        operations.add(builder.build())

        return try {
            resolver.applyBatch(ContactsContract.AUTHORITY, operations)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    @RequiresPermission(Manifest.permission.READ_CONTACTS)
    private fun getPhoneNumber(resolver: ContentResolver, contactId: String): String {
        val phoneCursor = resolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )

        var phone = ""
        phoneCursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                phone = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                ) ?: ""
            }
        }

        return phone
    }

    @RequiresPermission(Manifest.permission.READ_CONTACTS)
    private fun getContacts(resolver: ContentResolver): List<ContactInfo> {
        val contactsInfo = mutableListOf<ContactInfo>()
        val idsSet = mutableSetOf<String>() // For filter duplicate

        // Retrieve each part of the name and the ID
        val cursor = resolver.query(
            ContactsContract.Data.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID,
                ContactsContract.CommonDataKinds.StructuredName.PREFIX,
                ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
                ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME,
                ContactsContract.CommonDataKinds.StructuredName.SUFFIX
            ),
            ContactsContract.Data.MIMETYPE + " = ?",
            arrayOf(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE),
            null
        )

        if (cursor != null && cursor.count > 0) {
            // For each contact, get the image and the data
            while (cursor.moveToNext()) {
                val idValue =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID)
                val prefixValue =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PREFIX)
                val firstNameValue =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)
                val middleNameValue =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME)
                val lastNameValue =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)
                val suffixValue =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.SUFFIX)
                // Control the values, the contact must have at least a name to be imported
                if (idValue < 0 || firstNameValue < 0) continue

                // Given the column indexes, retrieve the values. Don't process duplicate ids
                val id = cursor.getString(idValue)
                if (idsSet.contains(id)) continue

                val unknownString = "??"
                val prefix = cursor.getStringOrNull(prefixValue) ?: ""
                val firstName = cursor.getString(firstNameValue) ?: unknownString
                val middleName = cursor.getStringOrNull(middleNameValue) ?: ""
                val lastName = cursor.getStringOrNull(lastNameValue) ?: ""
                val suffix = cursor.getStringOrNull(suffixValue) ?: ""
                val phone = getPhoneNumber(contactId = id, resolver = resolver)

                // The format at this time is first name, last name (+ extra stuff)
                val birthdayFirstName = "$prefix $firstName $middleName".replace(',', ' ').trim()
                val birthdayLastName = "$lastName $suffix".replace(',', ' ').trim()

                // Get the image, if any, and convert it to byte array
                val imageStream = ContactsContract.Contacts.openContactPhotoInputStream(
                    resolver,
                    ContentUris.withAppendedId(
                        ContactsContract.Contacts.CONTENT_URI,
                        id.toLong()
                    )
                )
                val bitmap = BitmapFactory.decodeStream(imageStream)
                var image: ByteArray? = null
                if (bitmap != null) {
                    // Check if the image is too big and resize it to a square if needed
                    var sizeImage = getBitmapSquareSize(bitmap)
                    if (sizeImage > 450) sizeImage = 450
                    val resizedBitmap = ThumbnailUtils.extractThumbnail(
                        bitmap,
                        sizeImage,
                        sizeImage,
                        ThumbnailUtils.OPTIONS_RECYCLE_INPUT,
                    )
                    image = bitmapToByteArray(resizedBitmap)
                }

                val contactInfo = ContactInfo(
                    id = id,
                    name = birthdayFirstName,
                    surname = birthdayLastName,
                    phone = phone,
                    image = image,
                )
                contactsInfo.add(contactInfo)
                idsSet.add(id)

                Log.d(
                    "import",
                    "birthday name is: ${contactInfo.name} ${contactInfo.surname} for id $id, phone = $phone"
                )
            }
        }

        cursor?.close()
        return contactsInfo
    }

    private fun getEventsForContact(
        contactInfo: ContactInfo,
        resolver: ContentResolver
    ): List<ImportedEvent> {
        // Retrieve the events for the current contact
        val eventCursor = resolver.query(
            ContactsContract.Data.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Event.DATA,
                ContactsContract.CommonDataKinds.Event.TYPE,
                ContactsContract.CommonDataKinds.Event.LABEL
            ),
            ContactsContract.Data.CONTACT_ID + " = " + contactInfo.id + " AND " +
                    ContactsContract.Data.MIMETYPE + " = '" +
                    ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "'",
            null,
            null
        )

        val events = mutableListOf<ImportedEvent>()

        if (eventCursor != null && eventCursor.count > 0) {
            while (eventCursor.moveToNext()) {
                val dateEvent: String = eventCursor.getString(0)
                val eventTypeNumber = eventCursor.getInt(1)
                lateinit var eventType: EventType
                var eventCustomLabel: String? = null

                // 0 is custom event type, 1 is anniversary, 2 is other, 3 is birthday
                when (eventTypeNumber) {
                    0 -> {
                        eventType = EventType.OTHER
                        eventCustomLabel = eventCursor.getString(2)
                    }

                    1 -> eventType = EventType.ANNIVERSARY
                    2 -> eventType = EventType.OTHER
                    else -> eventType = EventType.BIRTHDAY
                }

                events += ImportedEvent(
                    id = contactInfo.id,
                    name = contactInfo.name,
                    surname = contactInfo.surname,
                    eventDate = dateEvent,
                    image = contactInfo.image,
                    eventType = eventType,
                    customLabel = eventCustomLabel
                )
            }
        }

        eventCursor?.close()
        return events
    }

    @RequiresPermission(Manifest.permission.READ_CONTACTS)
    private fun getContactEvents(resolver: ContentResolver): List<ImportedEvent> {
        return getContacts(resolver).asSequence()
            .flatMap { getEventsForContact(it, resolver) }
            .toList()
    }

    @RequiresPermission(Manifest.permission.READ_CONTACTS)
    private fun getEventsFromContacts(resolver: ContentResolver): List<Event> {
        return getContactEvents(resolver).mapNotNull { contact ->
            val nameContact = contact.name.trim()
            val surnameContact = contact.surname.trim()
            var countYear = true
            val notes = contact.customLabel

            try {
                // Missing year, simply don't consider the year exactly like the contacts app does
                var parseDate = contact.eventDate
                if (parseDate.length < 8) {
                    parseDate = contact.eventDate.replaceFirst("-", "2025")
                    countYear = false
                }

                Event(
                    id = 0,
                    eventType = contact.eventType,
                    nameContact = nameContact,
                    surnameContact = surnameContact,
                    originalDate = LocalDate.parse(parseDate),
                    yearMatter = countYear,
                    image = contact.image,
                    notes = notes
                )
            } catch (_: Exception) {
                null
            }
        }
    }
}
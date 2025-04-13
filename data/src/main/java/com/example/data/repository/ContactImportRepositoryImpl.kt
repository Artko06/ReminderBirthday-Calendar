package com.example.data.repository

import android.Manifest
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
import com.example.domain.repository.ContactImportRepository

class ContactImportRepositoryImpl(
    private val contentResolver: ContentResolver
): ContactImportRepository {
    @RequiresPermission(Manifest.permission.READ_CONTACTS)
    override suspend fun importContacts(): List<ContactInfo> {
        return getContactEvents(contentResolver)
    }

    @RequiresPermission(Manifest.permission.READ_CONTACTS)
    private fun getContactEvents(resolver: ContentResolver): List<ContactInfo> {
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
                    image = image,
                )
                contactsInfo.add(contactInfo)
                idsSet.add(id)

                Log.d("import", "birthday name is: ${contactInfo.fullName} for id $id")
            }
        }

        cursor?.close()
        return contactsInfo
    }
}
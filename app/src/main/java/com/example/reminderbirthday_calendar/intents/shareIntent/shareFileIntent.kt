package com.example.reminderbirthday_calendar.intents.shareIntent

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


fun shareFileIntent(typeFile: TypeShareFile, context: Context): Intent {
    val file = File(context.getExternalFilesDir(null), "exported_events.${typeFile.name.lowercase()}")

    val fileUri: Uri = FileProvider.getUriForFile(
        /* context = */ context,
        /* authority = */ "${context.packageName}.fileprovider",
        /* file = */ file
    )

    val extensionFile = file.name.substringAfter(".")

    val sendIntent = Intent().apply<Intent> {
        action = Intent.ACTION_SEND
        type = "application/$extensionFile"
        putExtra(Intent.EXTRA_STREAM, fileUri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    return Intent.createChooser(sendIntent, null)
}
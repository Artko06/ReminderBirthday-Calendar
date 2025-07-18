package com.artkotlindev.reminderbirthday_calendar.intents.settingsAppIntent

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun settingsAppDetailsIntent(context: Context): Intent {
    return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
}
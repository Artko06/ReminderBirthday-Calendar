package com.example.reminderbirthday_calendar.intents.settingsNotification

import android.content.Context
import android.content.Intent
import android.provider.Settings

fun settingsNotificationIntent(context: Context): Intent {
    return Intent().apply {
        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    }
}
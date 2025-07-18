package com.artkotlindev.reminderbirthday_calendar.intents.openApp

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.artkotlindev.reminderbirthday_calendar.MainActivity

fun openAppIntent(context: Context): Intent = Intent(context, MainActivity::class.java).apply {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
}

fun pendingIntent(context: Context): PendingIntent = PendingIntent.getActivity(
    context,
    0,
    openAppIntent(context),
    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
)
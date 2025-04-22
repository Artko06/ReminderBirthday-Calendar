package com.example.reminderbirthday_calendar

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.example.reminderbirthday_calendar.notification.common.*
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CalendarApp: Application() {
    override fun onCreate() {
        super.onCreate()

        // DB
        FirebaseApp.initializeApp(this)

        // Notification Channel
        val notificationEventChannel = NotificationChannel(
            NOTIFICATION_EVENT_CHANNEL_ID,
            NOTIFICATION_EVENT_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationEventManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationEventManager.createNotificationChannel(notificationEventChannel)
    }
}
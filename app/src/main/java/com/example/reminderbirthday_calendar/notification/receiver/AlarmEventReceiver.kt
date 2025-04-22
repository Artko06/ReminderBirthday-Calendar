package com.example.reminderbirthday_calendar.notification.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.reminderbirthday_calendar.notification.common.*


class AlarmEventReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        val message = intent?.getStringExtra(EXTRA_MESSAGE_EVENT_NAME) ?: return
        println("Hello from BroadcastReceiver")

        val notificationManager = context.getSystemService(NotificationManager::class.java) as NotificationManager
        val notificationEvent = NotificationCompat.Builder(context, NOTIFICATION_EVENT_CHANNEL_ID)
            .setSmallIcon(com.example.reminderbirthday_calendar.R.drawable.ic_stat_birthday)
            .setColor(ContextCompat.getColor(context, com.example.reminderbirthday_calendar.R.color.blue)) // Установка цвета
            .setContentTitle("Birthday Reminder")
            .setContentText(message)
            .build()

        notificationManager.notify(NOTIFICATION_EVENT_ID, notificationEvent)
    }
}
package com.example.reminderbirthday_calendar.notification.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.reminderbirthday_calendar.notification.common.EXTRA_MESSAGE_EVENT_NAME
import com.example.reminderbirthday_calendar.notification.common.NOTIFICATION_EVENT_CHANNEL_ID
import com.example.reminderbirthday_calendar.notification.common.NOTIFICATION_EVENT_ID


class AlarmEventReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        val message = intent?.getStringExtra(EXTRA_MESSAGE_EVENT_NAME) ?: return
        println("Hello from BroadcastReceiver")

        val notificationManager = context.getSystemService(NotificationManager::class.java) as NotificationManager
        val notificationEvent = NotificationCompat.Builder(context, NOTIFICATION_EVENT_CHANNEL_ID)
            .setSmallIcon(com.example.reminderbirthday_calendar.R.drawable.ic_stat_birthday_2)
//            .setColor(ContextCompat.getColor(context, com.example.reminderbirthday_calendar.R.color.blue)) // Set background color
            .setContentTitle("Birthday Reminder")
            .setContentText(message)
            .build()

        notificationManager.notify(NOTIFICATION_EVENT_ID, notificationEvent)
    }
}
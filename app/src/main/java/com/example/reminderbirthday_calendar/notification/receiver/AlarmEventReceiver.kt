package com.example.reminderbirthday_calendar.notification.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.reminderbirthday_calendar.di.AlarmEventReceiverEntryPoint
import com.example.reminderbirthday_calendar.intents.openApp.pendingIntent
import com.example.reminderbirthday_calendar.notification.common.EXTRA_DAY_NOTIFICATION
import com.example.reminderbirthday_calendar.notification.common.EXTRA_HOUR_NOTIFICATION
import com.example.reminderbirthday_calendar.notification.common.EXTRA_ID_EVENT
import com.example.reminderbirthday_calendar.notification.common.EXTRA_MESSAGE_EVENT_NAME
import com.example.reminderbirthday_calendar.notification.common.EXTRA_MINUTE_NOTIFICATION
import com.example.reminderbirthday_calendar.notification.common.EXTRA_MONTH_NOTIFICATION
import com.example.reminderbirthday_calendar.notification.common.EXTRA_NUMBER_NOTIFICATION
import com.example.reminderbirthday_calendar.notification.common.NOTIFICATION_EVENT_CHANNEL_ID
import com.example.reminderbirthday_calendar.notification.common.NOTIFICATION_EVENT_ID
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate


class AlarmEventReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            println("Hello from BroadcastReceiver (Boot device)")

            val scheduleAllEventsUseCase = EntryPointAccessors.fromApplication(
                context = context.applicationContext,
                entryPoint = AlarmEventReceiverEntryPoint::class.java
            ).scheduleAllEventsUseCase()

            CoroutineScope(Dispatchers.IO).launch {
                scheduleAllEventsUseCase()
            }
        }

        val message = intent?.getStringExtra(EXTRA_MESSAGE_EVENT_NAME) ?: return
        val idEvent = intent.getLongExtra(EXTRA_ID_EVENT, -1)
        if (idEvent == -1L) return
        val numberNotification = intent.getIntExtra(EXTRA_NUMBER_NOTIFICATION, -1)
        if (numberNotification == -1) return
        val dayNotification = intent.getIntExtra(EXTRA_DAY_NOTIFICATION, -1)
        if (dayNotification == -1) return
        val monthNotification = intent.getIntExtra(EXTRA_MONTH_NOTIFICATION, -1)
        if (monthNotification == -1) return
        val hourNotification = intent.getIntExtra(EXTRA_HOUR_NOTIFICATION, -1)
        if (hourNotification == -1) return
        val minuteNotification = intent.getIntExtra(EXTRA_MINUTE_NOTIFICATION, -1)
        if (minuteNotification == -1) return
        println("Hello from BroadcastReceiver $dayNotification.$monthNotification($hourNotification:$minuteNotification) - $message")

        val notificationManager = context.getSystemService(NotificationManager::class.java) as NotificationManager
        val notificationEvent = NotificationCompat.Builder(context, NOTIFICATION_EVENT_CHANNEL_ID)
            .setSmallIcon(com.example.reminderbirthday_calendar.R.drawable.ic_stat_birthday_2)
//            .setColor(ContextCompat.getColor(context, com.example.reminderbirthday_calendar.R.color.blue)) // Set background color
            .setContentTitle("Birthday Reminder")
            .setContentText(message)
            .setContentIntent(pendingIntent(context = context))
            .setAutoCancel(true)
            .build()

        val nowDateTime = LocalDate.now().plusYears(1).atTime(hourNotification, minuteNotification)

        if (dayNotification == nowDateTime.dayOfMonth && monthNotification == nowDateTime.monthValue){
            notificationManager.notify(NOTIFICATION_EVENT_ID, notificationEvent)
        }

        val entryPoint = EntryPointAccessors.fromApplication(
            context = context.applicationContext,
            entryPoint = AlarmEventReceiverEntryPoint::class.java
        )

        val scheduleAlarmItemUseCase = entryPoint.scheduleAlarmItemUseCase()

        scheduleAlarmItemUseCase.invoke(
            id = idEvent,
            numberNotification = numberNotification,
            dateTime = nowDateTime,
            message = message
        )
    }
}
package com.example.reminderbirthday_calendar.notification.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.reminderbirthday_calendar.R
import com.example.reminderbirthday_calendar.di.reseiver.AlarmEventReceiverEntryPoint
import com.example.reminderbirthday_calendar.intents.openApp.pendingIntent
import com.example.reminderbirthday_calendar.notification.common.EXTRA_DAYS_BEFORE_EVENT
import com.example.reminderbirthday_calendar.notification.common.EXTRA_DAY_NOTIFICATION
import com.example.reminderbirthday_calendar.notification.common.EXTRA_HOUR_NOTIFICATION
import com.example.reminderbirthday_calendar.notification.common.EXTRA_ID_EVENT
import com.example.reminderbirthday_calendar.notification.common.EXTRA_MINUTE_NOTIFICATION
import com.example.reminderbirthday_calendar.notification.common.EXTRA_MONTH_NOTIFICATION
import com.example.reminderbirthday_calendar.notification.common.EXTRA_NAMES_ALARM_EVENT
import com.example.reminderbirthday_calendar.notification.common.EXTRA_NUMBER_NOTIFICATION
import com.example.reminderbirthday_calendar.notification.common.NOTIFICATION_EVENT_CHANNEL_ID
import dagger.hilt.android.EntryPointAccessors
import java.time.LocalDate

class AlarmEventReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        val namesAlarmEvent = intent?.getStringExtra(EXTRA_NAMES_ALARM_EVENT) ?: return
        val idEvent = intent.getLongExtra(EXTRA_ID_EVENT, -1)
        if (idEvent == -1L) return
        val numberNotification = intent.getIntExtra(EXTRA_NUMBER_NOTIFICATION, -1)
        if (numberNotification == -1) return
        val daysBeforeEvent = intent.getIntExtra(EXTRA_DAYS_BEFORE_EVENT, -1)
        if (daysBeforeEvent == -1) return
        val dayNotification = intent.getIntExtra(EXTRA_DAY_NOTIFICATION, -1)
        if (dayNotification == -1) return
        val monthNotification = intent.getIntExtra(EXTRA_MONTH_NOTIFICATION, -1)
        if (monthNotification == -1) return
        val hourNotification = intent.getIntExtra(EXTRA_HOUR_NOTIFICATION, -1)
        if (hourNotification == -1) return
        val minuteNotification = intent.getIntExtra(EXTRA_MINUTE_NOTIFICATION, -1)
        if (minuteNotification == -1) return
        println("Hello from BroadcastReceiver $dayNotification.$monthNotification($hourNotification:$minuteNotification) - $namesAlarmEvent")

        val nowDateTime = LocalDate.now()
            .plusYears(1)
            .atTime(hourNotification, minuteNotification)

        val message = buildString {
            append("\uD83C\uDF89 ") // Firework symbol

            append(
                if (daysBeforeEvent != 0) context.getString(R.string.congratulate)
                else context.getString(R.string.congratulate).uppercase()
            )

            append(" $namesAlarmEvent ")

            if (daysBeforeEvent != 0)
                append(context.getString(R.string.`in`) + " ")

            if (daysBeforeEvent == 0)
                append(context.getString(R.string.today).lowercase())
            else{
                append(daysBeforeEvent.toString())
                append(" " + context.resources
                    .getQuantityString(R.plurals.days, daysBeforeEvent, daysBeforeEvent))
            }
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java) as NotificationManager
        val notificationEvent = NotificationCompat.Builder(context, NOTIFICATION_EVENT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_birthday_2)
            .setContentTitle("Birthday Reminder")
            .setContentText(message)
            .setContentIntent(pendingIntent(context = context))
            .setAutoCancel(true)
            .build()

        if (dayNotification == nowDateTime.dayOfMonth && monthNotification == nowDateTime.monthValue){
            notificationManager.notify(numberNotification, notificationEvent)
        }

        val entryPoint = EntryPointAccessors.fromApplication(
            context = context.applicationContext,
            entryPoint = AlarmEventReceiverEntryPoint::class.java
        )

        val scheduleAlarmItemUseCase = entryPoint.scheduleAlarmItemUseCase()

        scheduleAlarmItemUseCase.invoke(
            id = idEvent,
            numberNotification = numberNotification,
            daysBeforeEvent = daysBeforeEvent,
            dateTime = nowDateTime,
            namesAlarmEvent = namesAlarmEvent
        )
    }
}
package com.artkotlindev.reminderbirthday_calendar.notification.schedulerImpl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.artkotlindev.domain.models.notification.AlarmEventItem
import com.artkotlindev.domain.repository.local.AlarmEventScheduler
import com.artkotlindev.reminderbirthday_calendar.notification.common.*
import com.artkotlindev.reminderbirthday_calendar.notification.receiver.AlarmEventReceiver
import java.time.ZoneId

class AlarmEventSchedulerImpl(
    private val context: Context
): AlarmEventScheduler
{
    val alarmManager: AlarmManager? = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: AlarmEventItem) {
        val intent = Intent(context, AlarmEventReceiver::class.java).apply {
            putExtra(EXTRA_ID_EVENT, item.id)
            putExtra(EXTRA_NAMES_ALARM_EVENT, item.namesAlarmEvent)
            putExtra(EXTRA_DAYS_BEFORE_EVENT, item.daysBeforeEvent)
            putExtra(EXTRA_NUMBER_NOTIFICATION, item.numberNotification)
            putExtra(EXTRA_HOUR_NOTIFICATION, item.dateTime.hour)
            putExtra(EXTRA_MINUTE_NOTIFICATION, item.dateTime.minute)
            putExtra(EXTRA_DAY_NOTIFICATION, item.dateTime.dayOfMonth)
            putExtra(EXTRA_MONTH_NOTIFICATION, item.dateTime.monthValue)
        }

        alarmManager?.setExactAndAllowWhileIdle(
            /* type = */ AlarmManager.RTC_WAKEUP,
            /* triggerAtMillis = */ item.dateTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            /* operation = */ PendingIntent.getBroadcast(
                /* context = */ context,
                /* requestCode = */ item.hashCode(),
                /* intent = */ intent,
                /* flags = */ PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }

    override fun cancel(item: AlarmEventItem) {
        alarmManager?.cancel(
            PendingIntent.getBroadcast(
                /* context = */ context,
                /* requestCode = */ item.hashCode(),
                /* intent = */ Intent(context, AlarmEventReceiver::class.java),
                /* flags = */ PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }

}
package com.example.reminderbirthday_calendar.notification.schedulerImpl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.reminderbirthday_calendar.notification.common.EXTRA_MESSAGE_EVENT_NAME
import com.example.reminderbirthday_calendar.notification.model.AlarmEventItem
import com.example.reminderbirthday_calendar.notification.receiver.AlarmEventReceiver
import com.example.reminderbirthday_calendar.notification.schedulerInterface.AlarmEventScheduler
import java.time.ZoneId

class AlarmEventSchedulerImpl(
    private val context: Context
): AlarmEventScheduler
{
    val alarmManager: AlarmManager? = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: AlarmEventItem) {
        val intent = Intent(context, AlarmEventReceiver::class.java).apply {
            putExtra(EXTRA_MESSAGE_EVENT_NAME, item.message)
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
package com.example.reminderbirthday_calendar.notification.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.reminderbirthday_calendar.di.reseiver.TimeZoneChangedReceiverEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimeZoneChangedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        if (intent?.action == Intent.ACTION_TIMEZONE_CHANGED) {
            println("Hello from BroadcastReceiver (TimeZone changed)")

            val scheduleAllEventsUseCase = EntryPointAccessors.fromApplication(
                context.applicationContext,
                TimeZoneChangedReceiverEntryPoint::class.java
            ).scheduleAllEventsUseCase()

            CoroutineScope(Dispatchers.IO).launch {
                scheduleAllEventsUseCase()
            }
        }
    }
}
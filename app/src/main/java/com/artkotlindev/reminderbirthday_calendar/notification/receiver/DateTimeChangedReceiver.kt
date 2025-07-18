package com.artkotlindev.reminderbirthday_calendar.notification.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.artkotlindev.reminderbirthday_calendar.di.reseiver.DateTimeChangedReceiverEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DateTimeChangedReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        if (intent?.action == Intent.ACTION_TIME_CHANGED) {
            println("Hello from BroadcastReceiver (Time changed)")

            val scheduleAllEventsUseCase = EntryPointAccessors.fromApplication(
                context.applicationContext,
                DateTimeChangedReceiverEntryPoint::class.java
            ).scheduleAllEventsUseCase()

            CoroutineScope(Dispatchers.IO).launch {
                scheduleAllEventsUseCase()
            }
        }
    }
}
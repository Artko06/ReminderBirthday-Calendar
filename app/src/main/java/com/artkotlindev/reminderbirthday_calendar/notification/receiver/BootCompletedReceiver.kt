package com.artkotlindev.reminderbirthday_calendar.notification.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.artkotlindev.reminderbirthday_calendar.di.reseiver.BootCompletedReceiverEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootCompletedReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            println("Hello from BroadcastReceiver (Boot device)")

            val scheduleAllEventsUseCase = EntryPointAccessors.fromApplication(
                context = context.applicationContext,
                entryPoint = BootCompletedReceiverEntryPoint::class.java
            ).scheduleAllEventsUseCase()

            CoroutineScope(Dispatchers.IO).launch {
                scheduleAllEventsUseCase()
            }
        }
    }
}
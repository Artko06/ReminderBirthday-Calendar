package com.artkotlindev.reminderbirthday_calendar

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.artkotlindev.data.local.util.init.InitDataStore
import com.artkotlindev.data.local.util.init.InitRoomDb
import com.artkotlindev.reminderbirthday_calendar.notification.common.NOTIFICATION_EVENT_CHANNEL_ID
import com.artkotlindev.reminderbirthday_calendar.notification.common.NOTIFICATION_EVENT_CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltAndroidApp
class CalendarApp: Application() {

    @Inject
    lateinit var initRoomDb: InitRoomDb

    override fun onCreate() {
        super.onCreate()

        // Preference datastore
        CoroutineScope(Dispatchers.IO).launch {
            InitDataStore.initDefaults(context = this@CalendarApp)
            initRoomDb.fillDataBase(context = this@CalendarApp)
        }

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
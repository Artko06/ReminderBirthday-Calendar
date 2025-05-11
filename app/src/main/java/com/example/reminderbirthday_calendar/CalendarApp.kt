package com.example.reminderbirthday_calendar

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.example.data.local.util.init.InitDataStore
import com.example.data.local.util.init.InitRoomDb
import com.example.reminderbirthday_calendar.notification.common.NOTIFICATION_EVENT_CHANNEL_ID
import com.example.reminderbirthday_calendar.notification.common.NOTIFICATION_EVENT_CHANNEL_NAME
import com.google.firebase.FirebaseApp
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

        // DB
        FirebaseApp.initializeApp(this)

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
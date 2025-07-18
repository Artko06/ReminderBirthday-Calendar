package com.artkotlindev.data.local.util.init

import android.content.Context
import com.artkotlindev.data.local.entity.settings.notification.NotificationEventEntity
import com.artkotlindev.data.local.roomDb.dao.SettingsDao
import com.artkotlindev.data.local.roomDb.database.CalendarInfoDB.Companion.DATABASE_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InitRoomDb @Inject constructor(
    private val settingsDao: SettingsDao
) {

    val daysBeforeEvent = listOf<Int>(0, 1, 3, 5, 7, 10, 14, 20, 30)

    val initNotificationEvent = List<NotificationEventEntity>(9) { index ->
        NotificationEventEntity(
            id = index + 1,
            hour = 9,
            minute = 0,
            daysBeforeEvent = daysBeforeEvent[index],
            statusOn = index <= 2
        )
    }

    fun isDatabaseExists(context: Context): Boolean {
        return context.getDatabasePath(DATABASE_NAME).exists()
    }


    fun fillDataBase(context: Context) {
        if (!isDatabaseExists(context = context)){
            println("Init room")
            CoroutineScope(Dispatchers.IO).launch {
                settingsDao.upsertNotificationEvents(notificationEvents = initNotificationEvent)
            }
        }
    }
}
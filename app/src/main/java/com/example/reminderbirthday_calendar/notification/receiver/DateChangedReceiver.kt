package com.example.reminderbirthday_calendar.notification.receiver

//class DateChangedReceiver: BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//        if (context == null) return
//
//        if (intent?.action == Intent.ACTION_DATE_CHANGED) {
//            println("Hello from BroadcastReceiver (Date changed)")
//
//            val scheduleAllEventsUseCase = EntryPointAccessors.fromApplication(
//                context.applicationContext,
//                DateChangedReceiverEntryPoint::class.java
//            ).scheduleAllEventsUseCase()
//
//            CoroutineScope(Dispatchers.IO).launch {
//                scheduleAllEventsUseCase()
//            }
//        }
//    }
//}
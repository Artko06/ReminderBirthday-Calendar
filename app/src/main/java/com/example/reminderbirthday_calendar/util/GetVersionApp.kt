package com.example.reminderbirthday_calendar.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

fun getAppVersion(context: Context): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.PackageInfoFlags.of(0)
        ).versionName ?: "1.0"
    } else {
        context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "1.0"
    }
}
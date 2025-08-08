package com.artkotlindev.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "settings")
val Context.dataStoreWithoutBackup by preferencesDataStore(name = "settings_without_backup")
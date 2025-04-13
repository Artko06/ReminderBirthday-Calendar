package com.example.data.local.roomDb.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.local.entity.settings.LanguageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao{
    // Language
    @Query("Select languageType FROM settings WHERE id = 1")
    fun getLanguageType(): Flow<String>

    @Upsert
    suspend fun upsertLanguage(language: LanguageEntity)

    // Theme
}
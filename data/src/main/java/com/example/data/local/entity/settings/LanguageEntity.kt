package com.example.data.local.entity.settings

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class LanguageEntity(
    @PrimaryKey
    val id: Int = 1,
    val languageType: String
)
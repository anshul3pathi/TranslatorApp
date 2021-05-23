package com.example.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.randomNumberGenerator
import com.squareup.moshi.Json

@Entity(tableName = "hindi_translations")
data class HindiTranslation(
    @PrimaryKey
    val word: String,
    val hindiTranslation: String,
    val id: Long = randomNumberGenerator(),
    var favourite: Boolean = false
)
package com.example.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.randomNumberGenerator
import com.squareup.moshi.Json

@Entity(tableName = "english_translations")
data class EnglishTranslation(
    val translations: List<String>,
    val examples: List<String>,
    val usage: String,
    var parentKey: String,
    @PrimaryKey
    var englishId: Long = randomNumberGenerator(),
)

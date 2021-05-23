package com.example.core.data.entities

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class HindiAndEnglishTranslation(
    @Embedded val hindi: HindiTranslation,
    @Relation(
        parentColumn = "word",
        entityColumn = "parentKey"
    )
    val englishTranslations: List<EnglishTranslation>
) : Serializable

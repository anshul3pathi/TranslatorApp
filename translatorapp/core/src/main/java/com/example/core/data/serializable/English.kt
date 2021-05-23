package com.example.core.data.serializable

import com.squareup.moshi.Json

data class English(
    @Json(name = "english_translations")
    val englishTranslations: List<String>,
    @Json(name = "english_examples")
    val englishExamples: List<String>,
    @Json(name = "usage")
    val usage: String
)

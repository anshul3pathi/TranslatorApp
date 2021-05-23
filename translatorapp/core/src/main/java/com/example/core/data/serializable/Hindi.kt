package com.example.core.data.serializable

import com.example.core.data.serializable.English
import com.squareup.moshi.Json

data class Hindi(
    @Transient
    var word: String = "",
    @Json(name = "hindi")
    val hindiTranslation: String,
    @Json(name = "english")
    val english: List<English>
)

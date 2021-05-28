package com.example.core.testUtils

import com.example.core.data.entities.EnglishTranslation
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.data.entities.HindiTranslation
import com.example.core.data.serializable.English
import com.example.core.data.serializable.Hindi

object ExampleDataTest {

    val englishEnglish1 = English(
        listOf(
            "having a high degree of heat or a high temperature",
            "(of food) containing or consisting of pungent spices or peppers which produce a burning sensation when tasted",
            "passionately enthusiastic, eager, or excited"
        ),
        listOf(
            "it was hot inside the hall",
            "a very hot dish cooked with green chili",
            "the idea had been nurtured in his hot imagination"
        ),
        "Adjective"
    )

    val hindiHindi1 = Hindi(
        "hot",
        "गरम",
        listOf(englishEnglish1)
    )

    val hindi1 = HindiTranslation(
        "hot",
        "गरम",
        id = 1L,
        favourite = false
    )

    val english1 = EnglishTranslation(
        listOf(
            "having a high degree of heat or a high temperature",
            "(of food) containing or consisting of pungent spices or peppers which produce a burning sensation when tasted",
            "passionately enthusiastic, eager, or excited"
        ),
        listOf(
            "it was hot inside the hall",
            "a very hot dish cooked with green chili",
            "the idea had been nurtured in his hot imagination"
        ),
        "Adjective",
        hindi1.word,
        11L
    )

    val hindi2 = HindiTranslation(
        "brave",
        "बहादुर",
        2L,
        false
    )

    val english22 = EnglishTranslation(
        listOf(
            "ready to face and endure danger or pain; showing courage"
        ),
        listOf(
            "a brave soldier"
        ),
        "Adjective",
        hindi2.word,
        22L
    )

    val english32 = EnglishTranslation(
        listOf(
            "a North American Indian warrior"
        ),
        listOf(""),
        "Noun\nDated",
        hindi2.word,
        33L
    )

    val english42 = EnglishTranslation(
        listOf(
            "endure or face (unpleasant conditions or behavior) without showing fear"
        ),
        listOf(
            "we had to brave the full heat of the sun"
        ),
        "Verb",
        hindi2.word,
        44L
    )

    val hindiAndEnglish1 = HindiAndEnglishTranslation(
        hindi = hindi1,
        englishTranslations = listOf(english1)
    )

    val hindiAndEnglish2 = HindiAndEnglishTranslation(
        hindi = hindi2,
        englishTranslations = listOf(
            english22,
            english32,
            english42
        )
    )

}
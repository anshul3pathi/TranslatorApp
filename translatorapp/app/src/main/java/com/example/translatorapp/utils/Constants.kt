package com.example.translatorapp.utils

import com.example.core.data.entities.EnglishTranslation
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.data.entities.HindiTranslation

object Constants {

    const val HOME_FRAGMENT_LOG_TAG = "HomeFragment"
    const val SETTINGS_FRAGMENT_LOG_TAG = "SettingsFragment"

    val EMPTY_TRANSLATION = HindiAndEnglishTranslation(
        hindi = HindiTranslation(
            word = "",
            hindiTranslation = "",
        ),
        englishTranslations = listOf(
            EnglishTranslation(
                listOf(""),
                listOf(""),
                "",
                ""
            )
        )
    )

}
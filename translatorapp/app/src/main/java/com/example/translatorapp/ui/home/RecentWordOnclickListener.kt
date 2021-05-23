package com.example.translatorapp.ui.home

import com.example.core.data.entities.HindiAndEnglishTranslation

interface RecentWordOnclickListener {

    fun onWordClicked(translation: HindiAndEnglishTranslation)
    fun onStarClicked(translation: HindiAndEnglishTranslation)

}
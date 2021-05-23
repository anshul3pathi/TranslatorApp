package com.example.translatorapp.ui.phrasebook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Result
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.data.entities.HindiTranslation
import com.example.core.repository.TranslationRepo
import com.example.core.succeeded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhraseBookViewModel @Inject constructor(
    private val repository: TranslationRepo
) : ViewModel() {

    private val _translations = MutableLiveData<List<HindiAndEnglishTranslation>>()
    val translations: LiveData<List<HindiAndEnglishTranslation>>
        get() = _translations

    init {
        updateTranslations()
    }

    private fun updateTranslations() {
        viewModelScope.launch(Dispatchers.IO) {
            val translations = repository.getAllTranslations()
            if (translations.succeeded) {
                translations as Result.Success
                withContext(Dispatchers.Main) {
                    val favouriteTranslation = translations.data.filter {
                        it.hindi.favourite
                    }
                    _translations.value = favouriteTranslation
                }
            }
        }
    }

    fun updateFavourite(hindiTranslation: HindiTranslation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavourite(hindiTranslation)
        }
        updateTranslations()
    }

}
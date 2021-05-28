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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhraseBookViewModel @Inject constructor(
    private val repository: TranslationRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _translations = MutableLiveData(listOf<HindiAndEnglishTranslation>())
    val translations: LiveData<List<HindiAndEnglishTranslation>>
        get() = _translations

    init {
        updateTranslations()
    }

    private fun updateTranslations() {
        viewModelScope.launch(dispatcher) {
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
        viewModelScope.launch(dispatcher) {
            repository.updateFavourite(hindiTranslation)
        }
        updateTranslations()
    }

    fun refreshTranslations() = updateTranslations()

}
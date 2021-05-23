package com.example.translatorapp.ui.splash

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.AppTheme
import com.example.core.repository.TranslationRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: TranslationRepo
) : ViewModel() {

    private val _navigateToMainActivity = MutableLiveData(false)
    val navigateToMainActivity: LiveData<Boolean>
        get() = _navigateToMainActivity

    private val _appTheme = MutableLiveData(getTheme())
    val appTheme: LiveData<AppTheme>
        get() = _appTheme

    init {
        startTimer()
    }

    private fun startTimer() {
        object: CountDownTimer(3500L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                _navigateToMainActivity.value = true
            }
        }.start()
    }

    private fun getTheme() = repository.getAppTheme()

}
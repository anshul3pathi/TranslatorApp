package com.example.translatorapp.utils

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.core.AppTheme
import com.google.android.material.snackbar.Snackbar

enum class NetworkStatus {
    LOADING,
    SUCCESS,
    ERROR,
    IDLE
}

fun displaySnackBar(text: String, layout: ViewGroup) {
    Snackbar.make(layout, text, Snackbar.LENGTH_LONG).show()
}

fun setAppTheme(appTheme: AppTheme) {
    if (appTheme == AppTheme.LIGHT) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}
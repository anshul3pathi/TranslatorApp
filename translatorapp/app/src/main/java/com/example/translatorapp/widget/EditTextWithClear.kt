package com.example.translatorapp.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.translatorapp.R

class EditTextWithClear(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatEditText(context, attrs, defStyle) {

    private val mClearButtonImage: Drawable =
            resources.getDrawable(R.drawable.ic_baseline_cancel_24, null)

    private fun showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                mClearButtonImage,
                null
        )
    }

    private fun hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                null,
                null
        )
    }

}
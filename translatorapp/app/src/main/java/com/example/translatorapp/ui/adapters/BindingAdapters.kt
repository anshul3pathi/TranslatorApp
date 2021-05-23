package com.example.translatorapp.ui.adapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.translatorapp.R
import com.example.translatorapp.utils.NetworkStatus

@BindingAdapter("rvGoneUnless")
fun rvGoneUnless(view: View, networkStatus: NetworkStatus) {
    view.visibility = if (networkStatus == NetworkStatus.IDLE) View.VISIBLE else View.GONE
}

@BindingAdapter("translationContainerGoneUnless")
fun translationContainerGoneUnless(view: View, networkStatus: NetworkStatus) {
    view.visibility = if (networkStatus == NetworkStatus.SUCCESS) View.VISIBLE else View.GONE
}

@BindingAdapter("progressIndicatorGoneUnless")
fun progressIndicatorGoneUnless(view: View, networkStatus: NetworkStatus) {
    view.visibility = if (networkStatus == NetworkStatus.LOADING) View.VISIBLE else View.GONE
}

@BindingAdapter("setFavouriteRecent")
fun setFavouriteRecent(view: ImageView, favourite: Boolean) {
    if (favourite) {
        Glide.with(view).load(R.drawable.ic_star_filled_24).into(view)
    } else {
        view.setImageDrawable(view.context.getDrawable(R.drawable.ic_star_border_black))
    }
}

@BindingAdapter("setFavouriteTranslationContainer")
fun setFavouriteTranslationContainer(view: ImageView, favourite: Boolean) {
    if (favourite) {
        Glide.with(view).load(R.drawable.ic_star_filled_24).into(view)
    } else {
        Glide.with(view).load(R.drawable.ic_star_border_white).into(view)
    }
}
package com.example.chcmovies.ui.adapters

import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableInt
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chcmovies.R

@BindingAdapter("imageUrl")
fun bindImageViewUrl(imageView: ImageView, url: String) {
    val requestOption = RequestOptions()
        .centerCrop()

    Glide.with(imageView.context).load(url)
        .apply(requestOption)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(imageView)
}

@BindingAdapter("imageBtnSrc")
fun bindImageButtonSrc(imageButton: ImageButton?, resId: ObservableInt?) {
    resId?.get()?.let {
        imageButton?.setImageResource(resId.get())
    }
}
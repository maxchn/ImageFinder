package com.imagefinder.features.featuresMain.common

import androidx.databinding.BindingAdapter
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.imagefinder.R

@BindingAdapter("app:imageUrl")
fun loadImage(view: AppCompatImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.ic_placeholder)
        .into(view)
}

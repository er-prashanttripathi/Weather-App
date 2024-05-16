

package com.app.ecom.weatherApp.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.app.ecom.weatherApp.R
import com.bumptech.glide.Glide

class LoadImageExt {
    fun ImageView.loadImage(
        url: String,
        @DrawableRes placeholder: Int = R.drawable.ic_edit
    ) {
        Glide.with(this)
            .load(url)
            .placeholder(placeholder)
            .into(this)
    }

    /* Usage:  Load image with extension
    img_profile_picture.loadImage(url)

    img_profile_picture.loadImage(url, R.drawable.custom_placeholder)
    */
}
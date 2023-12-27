package com.example.task_3.utils.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.task_3.R

fun ImageView.loadImage(image: String? = null) {
//    log("load image " + image.toString())
    Glide.with(this)
        .load(image)
        .centerCrop()
        .circleCrop()
        .placeholder(R.drawable.ic_user_photo)
        .error(
            Glide.with(this)
                .load(R.drawable.ic_user_photo)
                .centerCrop()
                .circleCrop()
        )
        .into(this)
}
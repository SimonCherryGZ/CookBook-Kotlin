package com.simoncherry.cookbookkotlin

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by Simon on 2017/5/24.
 */

fun ImageView.loadUrl(url: String?) {
    if (url != null) {
        Glide.with(context).load(url)
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .into(this)
    } else {
        this.setImageResource(R.drawable.default_img)
    }
}
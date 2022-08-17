package com.erhansen.weatherapp.utils

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

fun imageViewProgressBar(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}
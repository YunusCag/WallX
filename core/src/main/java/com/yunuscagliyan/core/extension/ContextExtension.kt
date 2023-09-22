package com.yunuscagliyan.core.extension

import android.content.Context

fun Context.getDeviceWidthAndHeight():Pair<Int,Int>{
    val metrics = this.resources.displayMetrics
    val screenWidth = metrics.widthPixels
    val screenHeight = metrics.heightPixels

    return Pair(screenWidth, screenHeight)
}
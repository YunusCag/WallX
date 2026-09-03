package com.yunuscagliyan.core.util

/**
 * inSampleSize for BitmapFactory.
 *
 * Wallpapers were decoded at full size, which is a needless allocation for a 1280px
 * image and an outright crash risk once Pixabay full API access starts returning
 * originals several thousand pixels wide.
 */
object BitmapSampling {

    fun calculateInSampleSize(
        sourceWidth: Int,
        sourceHeight: Int,
        requestedWidth: Int,
        requestedHeight: Int,
    ): Int {
        if (sourceWidth <= 0 || sourceHeight <= 0) return 1
        if (requestedWidth <= 0 || requestedHeight <= 0) return 1

        var inSampleSize = 1
        while (sourceWidth / (inSampleSize * 2) >= requestedWidth &&
            sourceHeight / (inSampleSize * 2) >= requestedHeight
        ) {
            inSampleSize *= 2
        }
        return inSampleSize
    }
}

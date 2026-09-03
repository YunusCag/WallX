package com.yunuscagliyan.core.util

/** Crop rectangle in source-image pixels. */
data class CropRect(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int,
) {
    val width: Int get() = right - left
    val height: Int get() = bottom - top
}

/**
 * Works out the visible crop hint handed to WallpaperManager.setBitmap.
 *
 * Without one the system stretches whatever it is given to fill the screen, so a
 * landscape photo on a tall phone came out blurry and badly framed. Taking the
 * largest centred region that already matches the screen's aspect ratio keeps the
 * pixels the device actually shows at their original resolution.
 */
object WallpaperCrop {

    fun centerCropHint(
        sourceWidth: Int,
        sourceHeight: Int,
        screenWidth: Int,
        screenHeight: Int,
    ): CropRect? {
        if (sourceWidth <= 0 || sourceHeight <= 0) return null
        if (screenWidth <= 0 || screenHeight <= 0) return null

        val targetAspect = screenWidth.toDouble() / screenHeight.toDouble()
        val sourceAspect = sourceWidth.toDouble() / sourceHeight.toDouble()

        val cropWidth: Int
        val cropHeight: Int
        if (sourceAspect > targetAspect) {
            // Source is wider than the screen: keep the full height, trim the sides.
            cropHeight = sourceHeight
            cropWidth = (sourceHeight * targetAspect).toInt().coerceIn(1, sourceWidth)
        } else {
            // Source is taller: keep the full width, trim top and bottom.
            cropWidth = sourceWidth
            cropHeight = (sourceWidth / targetAspect).toInt().coerceIn(1, sourceHeight)
        }

        val left = (sourceWidth - cropWidth) / 2
        val top = (sourceHeight - cropHeight) / 2
        return CropRect(
            left = left,
            top = top,
            right = left + cropWidth,
            bottom = top + cropHeight,
        )
    }
}

package com.yunuscagliyan.core.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class WallpaperCropTest {

    private val screenWidth = 1080
    private val screenHeight = 2400

    @Test
    fun `a landscape photo is trimmed at the sides not squashed`() {
        val crop = WallpaperCrop.centerCropHint(1280, 853, screenWidth, screenHeight)!!

        assertEquals(853, crop.height)
        assertEquals(383, crop.width)
        assertTrue(crop.width < 1280)
    }

    @Test
    fun `a photo taller than the screen is trimmed top and bottom`() {
        val crop = WallpaperCrop.centerCropHint(1080, 4000, screenWidth, screenHeight)!!

        assertEquals(1080, crop.width)
        assertEquals(2400, crop.height)
    }

    @Test
    fun `the crop is centred`() {
        val crop = WallpaperCrop.centerCropHint(1280, 853, screenWidth, screenHeight)!!

        assertEquals((1280 - crop.width) / 2, crop.left)
        assertEquals(crop.left + crop.width, crop.right)
        assertEquals(0, crop.top)
        assertEquals(853, crop.bottom)
    }

    @Test
    fun `a photo already at the screen aspect ratio is kept whole`() {
        val crop = WallpaperCrop.centerCropHint(1080, 2400, screenWidth, screenHeight)!!

        assertEquals(0, crop.left)
        assertEquals(0, crop.top)
        assertEquals(1080, crop.right)
        assertEquals(2400, crop.bottom)
    }

    @Test
    fun `the crop never leaves the source bounds`() {
        val sizes = listOf(1 to 1, 4000 to 10, 10 to 4000, 1920 to 1080, 640 to 640)

        sizes.forEach { (width, height) ->
            val crop = WallpaperCrop.centerCropHint(width, height, screenWidth, screenHeight)!!
            assertTrue("left for $width x $height", crop.left >= 0)
            assertTrue("top for $width x $height", crop.top >= 0)
            assertTrue("right for $width x $height", crop.right <= width)
            assertTrue("bottom for $width x $height", crop.bottom <= height)
            assertTrue("width for $width x $height", crop.width >= 1)
            assertTrue("height for $width x $height", crop.height >= 1)
        }
    }

    @Test
    fun `the crop keeps the screen aspect ratio`() {
        val crop = WallpaperCrop.centerCropHint(4000, 3000, screenWidth, screenHeight)!!

        val cropAspect = crop.width.toDouble() / crop.height
        val screenAspect = screenWidth.toDouble() / screenHeight
        assertTrue(kotlin.math.abs(cropAspect - screenAspect) < 0.01)
    }

    @Test
    fun `invalid dimensions produce no hint`() {
        assertNull(WallpaperCrop.centerCropHint(0, 100, screenWidth, screenHeight))
        assertNull(WallpaperCrop.centerCropHint(100, 0, screenWidth, screenHeight))
        assertNull(WallpaperCrop.centerCropHint(100, 100, 0, screenHeight))
        assertNull(WallpaperCrop.centerCropHint(100, 100, screenWidth, -1))
    }
}

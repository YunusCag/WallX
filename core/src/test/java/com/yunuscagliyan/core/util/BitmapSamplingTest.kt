package com.yunuscagliyan.core.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BitmapSamplingTest {

    @Test
    fun `an image at or below the requested size is not sampled`() {
        assertEquals(1, BitmapSampling.calculateInSampleSize(1080, 2400, 1080, 2400))
        assertEquals(1, BitmapSampling.calculateInSampleSize(640, 480, 1080, 2400))
    }

    @Test
    fun `an oversized image is halved until it fits`() {
        assertEquals(2, BitmapSampling.calculateInSampleSize(2160, 4800, 1080, 2400))
        assertEquals(4, BitmapSampling.calculateInSampleSize(4320, 9600, 1080, 2400))
    }

    @Test
    fun `sampling never drops below the requested size on either axis`() {
        val sample = BitmapSampling.calculateInSampleSize(5000, 3000, 1080, 2400)

        assertTrue(5000 / sample >= 1080)
        assertTrue(3000 / sample >= 2400 || sample == 1)
    }

    @Test
    fun `the result is always a power of two`() {
        listOf(1280 to 853, 4000 to 3000, 8000 to 6000, 12000 to 9000).forEach { (w, h) ->
            val sample = BitmapSampling.calculateInSampleSize(w, h, 1080, 2400)
            assertTrue("$w x $h gave $sample", sample > 0 && (sample and (sample - 1)) == 0)
        }
    }

    @Test
    fun `unknown bounds fall back to no sampling`() {
        assertEquals(1, BitmapSampling.calculateInSampleSize(-1, -1, 1080, 2400))
        assertEquals(1, BitmapSampling.calculateInSampleSize(1080, 2400, 0, 0))
    }
}

package com.yunuscagliyan.core.util

import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PhotoQualityTest {

    private fun photo(
        preview: String? = "preview_150.jpg",
        webformat: String? = "webformat_640.jpg",
        large: String? = "large_1280.jpg",
        fullHd: String? = null,
        original: String? = null,
    ) = PhotoModel(
        previewURL = preview,
        webformatURL = webformat,
        largeImageURL = large,
        fullHDURL = fullHd,
        imageURL = original,
    )

    @Test
    fun `standard api key falls back to the 1280px image`() {
        assertEquals("large_1280.jpg", PhotoQuality.bestImageUrl(photo()))
    }

    @Test
    fun `full hd is preferred over large when the account has full access`() {
        val result = PhotoQuality.bestImageUrl(photo(fullHd = "fullhd_1920.jpg"))

        assertEquals("fullhd_1920.jpg", result)
    }

    @Test
    fun `original wins over every other size`() {
        val result = PhotoQuality.bestImageUrl(
            photo(fullHd = "fullhd_1920.jpg", original = "original.jpg")
        )

        assertEquals("original.jpg", result)
    }

    @Test
    fun `blank urls are skipped rather than returned`() {
        val result = PhotoQuality.bestImageUrl(
            photo(large = "", fullHd = "   ", original = null)
        )

        assertEquals("webformat_640.jpg", result)
    }

    @Test
    fun `falls all the way back to the preview`() {
        val result = PhotoQuality.bestImageUrl(
            photo(webformat = null, large = null)
        )

        assertEquals("preview_150.jpg", result)
    }

    @Test
    fun `returns null when the photo carries no url at all`() {
        assertNull(PhotoQuality.bestImageUrl(photo(null, null, null)))
        assertNull(PhotoQuality.bestImageUrl(null))
    }

    @Test
    fun `thumbnails stay on the cheap sizes even when a bigger one exists`() {
        val result = PhotoQuality.thumbnailUrl(
            photo(fullHd = "fullhd_1920.jpg", original = "original.jpg")
        )

        assertEquals("webformat_640.jpg", result)
    }

    @Test
    fun `thumbnail falls back to preview then to large`() {
        assertEquals(
            "preview_150.jpg",
            PhotoQuality.thumbnailUrl(photo(webformat = null)),
        )
        assertEquals(
            "large_1280.jpg",
            PhotoQuality.thumbnailUrl(photo(preview = null, webformat = null)),
        )
    }
}

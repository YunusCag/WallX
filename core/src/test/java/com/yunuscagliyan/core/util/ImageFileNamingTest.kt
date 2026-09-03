package com.yunuscagliyan.core.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ImageFileNamingTest {

    @Test
    fun `keeps the extension the server actually serves`() {
        assertEquals("jpg", ImageFileNaming.extensionOf("https://cdn.pixabay.com/photo/a_1280.jpg"))
        assertEquals("png", ImageFileNaming.extensionOf("https://cdn.pixabay.com/photo/a_1280.png"))
        assertEquals("webp", ImageFileNaming.extensionOf("https://cdn.pixabay.com/photo/a.webp"))
    }

    @Test
    fun `ignores query strings and fragments`() {
        assertEquals(
            "jpg",
            ImageFileNaming.extensionOf("https://cdn.pixabay.com/photo/a_1280.jpg?attachment=1&key=abc"),
        )
        assertEquals("png", ImageFileNaming.extensionOf("https://cdn.pixabay.com/a.png#preview"))
    }

    @Test
    fun `falls back to jpg for unknown or missing extensions`() {
        assertEquals("jpg", ImageFileNaming.extensionOf("https://cdn.pixabay.com/photo/a_1280"))
        assertEquals("jpg", ImageFileNaming.extensionOf("https://cdn.pixabay.com/photo/a.gif"))
        assertEquals("jpg", ImageFileNaming.extensionOf(null))
        assertEquals("jpg", ImageFileNaming.extensionOf(""))
    }

    @Test
    fun `a dot in the path does not become the extension`() {
        assertEquals("jpg", ImageFileNaming.extensionOf("https://cdn.pixabay.com/v1.2/photo/wallpaper"))
    }

    @Test
    fun `extension is case insensitive`() {
        assertEquals("png", ImageFileNaming.extensionOf("https://cdn.pixabay.com/photo/A_1280.PNG"))
    }

    @Test
    fun `mime type follows the extension`() {
        assertEquals("image/jpeg", ImageFileNaming.mimeTypeOf("a.jpg"))
        assertEquals("image/jpeg", ImageFileNaming.mimeTypeOf("a.jpeg"))
        assertEquals("image/png", ImageFileNaming.mimeTypeOf("a.png"))
        assertEquals("image/webp", ImageFileNaming.mimeTypeOf("a.webp"))
        assertEquals("image/jpeg", ImageFileNaming.mimeTypeOf("a.gif"))
    }

    @Test
    fun `file name is prefixed and carries the right extension`() {
        val name = ImageFileNaming.fileNameFor("https://cdn.pixabay.com/photo/a_1280.jpg", id = "abc")

        assertEquals("WallX_abc.jpg", name)
    }

    @Test
    fun `generated names are unique`() {
        val first = ImageFileNaming.fileNameFor("a.jpg")
        val second = ImageFileNaming.fileNameFor("a.jpg")

        assertTrue(first != second)
    }
}

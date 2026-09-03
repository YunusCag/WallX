package com.yunuscagliyan.core.util

import java.util.UUID

/**
 * Names for saved wallpapers.
 *
 * The extension is taken from the source URL instead of being hardcoded: downloads
 * used to be written as ".png" whatever the bytes actually were, and Pixabay serves
 * JPEG, so gallery apps were handed a file whose name contradicted its contents.
 */
object ImageFileNaming {

    private const val DEFAULT_EXTENSION = "jpg"
    private val SUPPORTED_EXTENSIONS = setOf("jpg", "jpeg", "png", "webp")

    fun extensionOf(imageUrl: String?): String {
        val candidate = imageUrl
            ?.substringBefore('?')
            ?.substringBefore('#')
            ?.substringAfterLast('/')
            ?.substringAfterLast('.', "")
            ?.lowercase()
            .orEmpty()
        return if (candidate in SUPPORTED_EXTENSIONS) candidate else DEFAULT_EXTENSION
    }

    fun mimeTypeOf(imageUrl: String?): String = when (extensionOf(imageUrl)) {
        "png" -> "image/png"
        "webp" -> "image/webp"
        else -> "image/jpeg"
    }

    fun fileNameFor(
        imageUrl: String?,
        id: String = UUID.randomUUID().toString(),
    ): String = "${Constant.FileUtil.PUBLIC_FOLDER_NAME}_$id.${extensionOf(imageUrl)}"
}

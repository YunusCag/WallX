package com.yunuscagliyan.core.util

import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel

/**
 * Picks the sharpest image Pixabay gave us for a photo.
 *
 * Only previewURL (150px), webformatURL (640px) and largeImageURL (1280px) come back
 * on a standard API key. fullHDURL (1920px) and imageURL (original) are populated
 * once the account is approved for full API access, so preferring them here means
 * the app picks up the better assets without any further change.
 */
object PhotoQuality {

    /** Best available source for setting a wallpaper or zooming into a photo. */
    fun bestImageUrl(photo: PhotoModel?): String? = photo?.let {
        it.imageURL.orNull()
            ?: it.fullHDURL.orNull()
            ?: it.largeImageURL.orNull()
            ?: it.webformatURL.orNull()
            ?: it.previewURL.orNull()
    }

    /** Cheap source for grid thumbnails, where 640px is plenty. */
    fun thumbnailUrl(photo: PhotoModel?): String? = photo?.let {
        it.webformatURL.orNull() ?: it.previewURL.orNull() ?: it.largeImageURL.orNull()
    }

    private fun String?.orNull(): String? = this?.takeIf { it.isNotBlank() }
}

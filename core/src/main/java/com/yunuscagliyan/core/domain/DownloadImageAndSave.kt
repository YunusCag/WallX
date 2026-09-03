package com.yunuscagliyan.core.domain

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.yunuscagliyan.core.data.remote.service.PixabayService
import com.yunuscagliyan.core.util.Constant
import com.yunuscagliyan.core.util.DownloadState
import com.yunuscagliyan.core.util.ImageFileNaming
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class DownloadImageAndSave @Inject constructor(
    private val pixabayService: PixabayService,
    @ApplicationContext private val context: Context,
) {
    operator fun invoke(
        imageUrl: String,
        triggerUrl: String?
    ): Flow<DownloadState> = flow {
        try {
            emit(DownloadState.Loading)
            val response = pixabayService.downloadImage(imageUrl = imageUrl)
            triggerUrl?.let { pixabayService.triggerDownload(url = it) }

            val fileName = ImageFileNaming.fileNameFor(imageUrl)
            Timber.d("Saving download as $fileName")
            response.byteStream().use { input ->
                save(input = input, fileName = fileName, mimeType = ImageFileNaming.mimeTypeOf(imageUrl))
            }
            emit(DownloadState.Finished)
        } catch (e: Exception) {
            Timber.e(e, "Could not save the download")
            emit(DownloadState.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    private fun save(input: InputStream, fileName: String, mimeType: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveWithMediaStore(input, fileName, mimeType)
        } else {
            saveWithFileApi(input, fileName, mimeType)
        }
    }

    /**
     * Scoped storage: from Android 10 on, writing straight into the public Pictures
     * directory is not allowed (requestLegacyExternalStorage stopped being honoured
     * in Android 11), and only MediaStore entries show up in gallery apps.
     */
    private fun saveWithMediaStore(input: InputStream, fileName: String, mimeType: String) {
        val resolver = context.contentResolver
        val pending = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
            put(
                MediaStore.Images.Media.RELATIVE_PATH,
                "${Environment.DIRECTORY_PICTURES}/${Constant.FileUtil.PUBLIC_FOLDER_NAME}"
            )
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, pending)
            ?: throw IOException("MediaStore refused to create an entry for $fileName")

        try {
            resolver.openOutputStream(uri)?.use { output -> input.copyTo(output) }
                ?: throw IOException("Could not open an output stream for $uri")

            resolver.update(
                uri,
                ContentValues().apply { put(MediaStore.Images.Media.IS_PENDING, 0) },
                null,
                null
            )
        } catch (e: Exception) {
            // Leaving the row behind would show as a permanently pending, empty image.
            resolver.delete(uri, null, null)
            throw e
        }
    }

    private fun saveWithFileApi(input: InputStream, fileName: String, mimeType: String) {
        val directory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            Constant.FileUtil.PUBLIC_FOLDER_NAME
        )
        if (!directory.exists() && !directory.mkdirs()) {
            throw IOException("Could not create ${directory.absolutePath}")
        }

        val file = File(directory, fileName)
        FileOutputStream(file).use { output -> input.copyTo(output) }

        // Without this the file exists but no gallery lists it until the next reboot.
        MediaScannerConnection.scanFile(
            context,
            arrayOf(file.absolutePath),
            arrayOf(mimeType),
            null
        )
    }
}

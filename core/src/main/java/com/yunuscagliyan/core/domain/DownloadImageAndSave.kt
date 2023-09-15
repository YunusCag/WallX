package com.yunuscagliyan.core.domain

import android.os.Environment
import com.yunuscagliyan.core.data.remote.service.UnsplashService
import com.yunuscagliyan.core.util.Constant
import com.yunuscagliyan.core.util.DownloadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject

class DownloadImageAndSave @Inject constructor(
    private val unsplashService: UnsplashService
) {
    operator fun invoke(
        imageUrl: String
    ): Flow<DownloadState> = flow {
        try {
            emit(DownloadState.Loading)
            val response = unsplashService.downloadImage(
                imageUrl = imageUrl
            )
            val inputStream = response.byteStream()
            val filesDir =
                File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    Constant.FileUtil.PUBLIC_FOLDER_NAME
                )
            if (!filesDir.exists()) {
                filesDir.mkdir()
            }
            val fileName = UUID.randomUUID().toString() + ".png"
            Timber.d("File Name:$fileName")
            val file = File(filesDir, fileName)
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.flush()
            outputStream.close()
            emit(DownloadState.Finished)
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
            emit(DownloadState.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}
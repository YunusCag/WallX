package com.yunuscagliyan.core.util

sealed class DownloadState {
    object Loading : DownloadState()
    object Finished : DownloadState()
    data class Error(val error: Throwable) : DownloadState()
}

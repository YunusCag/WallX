package com.yunuscagliyan.core_ui.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.yunuscagliyan.core.util.Constant
import com.yunuscagliyan.core_ui.extension.toColor
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun WallImage(
    modifier: Modifier = Modifier,
    url: String?,
    hexColor: String? = null,
    contentScale: ContentScale = ContentScale.FillWidth,
    size: Size = Size.ORIGINAL
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .size(size)
            .crossfade(true)
            .build()
    )
    when (painter.state) {
        is AsyncImagePainter.State.Error -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(hexColor?.toColor() ?: WallXAppTheme.colors.secondary)
            )
        }

        is AsyncImagePainter.State.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(hexColor?.toColor() ?: WallXAppTheme.colors.secondary)
            )
        }

        is AsyncImagePainter.State.Success -> {
            Image(
                modifier = modifier
                    .fillMaxSize(),
                painter = painter,
                contentDescription = Constant.StringParameter.EMPTY_STRING,
                contentScale = contentScale
            )
        }

        else -> {
            Image(
                modifier = modifier
                    .fillMaxSize(),
                painter = painter,
                contentDescription = Constant.StringParameter.EMPTY_STRING,
                contentScale = contentScale
            )
        }
    }
}
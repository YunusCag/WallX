package com.yunuscagliyan.core_ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.yunuscagliyan.core.util.Constant.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.extension.toColor
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun PhotoImageCard(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    hexColor: String?,
    contentScale: ContentScale = ContentScale.FillWidth,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = WallXAppTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
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
                    contentDescription = EMPTY_STRING,
                    contentScale = contentScale
                )
            }

            else -> {
                Image(
                    modifier = modifier
                        .fillMaxSize(),
                    painter = painter,
                    contentDescription = EMPTY_STRING,
                    contentScale = contentScale
                )
            }
        }
    }
}
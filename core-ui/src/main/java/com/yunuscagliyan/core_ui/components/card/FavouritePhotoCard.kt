package com.yunuscagliyan.core_ui.components.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.yunuscagliyan.core_ui.components.button.FavouriteButton
import com.yunuscagliyan.core_ui.components.image.WallImage
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun FavouritePhotoCard(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    hexColor: String?=null,
    onFavouriteClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = WallXAppTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            WallImage(
                modifier = Modifier
                    .fillMaxSize(),
                url = imageUrl,
                hexColor = hexColor,
                contentScale = ContentScale.Crop
            )

            FavouriteButton(
                modifier = Modifier
                    .padding(WallXAppTheme.dimension.paddingSmall2),
                isFavourite = true,
                onClick = {
                    onFavouriteClick()
                },
            )
        }

    }
}
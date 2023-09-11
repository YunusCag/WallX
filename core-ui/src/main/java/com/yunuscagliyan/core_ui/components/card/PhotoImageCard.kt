package com.yunuscagliyan.core_ui.components.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yunuscagliyan.core_ui.components.image.WallImage
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun PhotoImageCard(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    hexColor: String?,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = WallXAppTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        WallImage(
            url = imageUrl,
            hexColor = hexColor
        )
    }
}
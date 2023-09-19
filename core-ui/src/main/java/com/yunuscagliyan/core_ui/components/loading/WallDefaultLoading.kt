package com.yunuscagliyan.core_ui.components.loading

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun WallDefaultLoading(
    modifier: Modifier = Modifier,
    color: Color = WallXAppTheme.colors.white,
    progress: Float = 0f,
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        trackColor = Color.Transparent,
        strokeWidth = WallXAppTheme.dimension.circularLoadingWidth,
        strokeCap = StrokeCap.Round,
    )

}
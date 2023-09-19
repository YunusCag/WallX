package com.yunuscagliyan.core_ui.components.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.yunuscagliyan.core.util.Constant.DurationUtil.DEFAULT_ANIMATION_BUTTON_DURATION
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun FavouriteButton(
    modifier: Modifier = Modifier,
    isFavourite: Boolean = false,
    size: Dp = WallXAppTheme.dimension.favouriteButtonSize,
    selectedColor: Color = WallXAppTheme.colors.white,
    unSelectedColor: Color = WallXAppTheme.colors.white,
    onClick: (Boolean) -> Unit,
) {

    val colorAnim = animateColorAsState(
        targetValue = if (isFavourite)
            selectedColor
        else
            unSelectedColor,
        animationSpec = tween(DEFAULT_ANIMATION_BUTTON_DURATION),
        label = "favourite_button_color_anim"
    )


    val scale = animateFloatAsState(
        targetValue = if (isFavourite) 1f else 0.9f,
        animationSpec = tween(DEFAULT_ANIMATION_BUTTON_DURATION),
        label = "favourite_button_scale_anim"
    )

    IconToggleButton(
        checked = isFavourite,
        onCheckedChange = {
            onClick(!isFavourite)
        }
    ) {
        Icon(
            tint = colorAnim.value,
            modifier = modifier
                .size(size)
                .scale(scale.value),
            imageVector = if (isFavourite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }

}
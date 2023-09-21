package com.yunuscagliyan.core_ui.components.tile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun SettingItemTile(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes icon: Int,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = WallXAppTheme.dimension.paddingSmall3,
                vertical = WallXAppTheme.dimension.paddingMedium1
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = WallXAppTheme.colors.textPrimary,
            contentDescription = title,
            modifier = Modifier
                .size(WallXAppTheme.dimension.iconSizeMedium)
        )
        Spacer(modifier = Modifier.width(WallXAppTheme.dimension.paddingSmall2))
        Text(
            text = title,
            style = WallXAppTheme.typography.normal1,
            color = WallXAppTheme.colors.textPrimary,
            modifier = Modifier
                .weight(1f)
        )
        Icon(
            Icons.Filled.KeyboardArrowRight,
            tint = WallXAppTheme.colors.secondaryGray,
            contentDescription = title,
            modifier = Modifier
                .size(WallXAppTheme.dimension.iconSizeSmall)
        )
    }
}
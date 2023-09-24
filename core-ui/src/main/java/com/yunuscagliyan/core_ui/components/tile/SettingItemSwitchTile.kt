package com.yunuscagliyan.core_ui.components.tile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun SettingItemSwitchTile(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes icon: Int,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit
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
        Switch(
            checked = checked,
            colors = SwitchDefaults.colors(
                checkedThumbColor = WallXAppTheme.colors.white,
                checkedTrackColor = WallXAppTheme.colors.secondary,

                uncheckedThumbColor = WallXAppTheme.colors.secondaryGray,
                uncheckedTrackColor = WallXAppTheme.colors.background,

                uncheckedBorderColor = WallXAppTheme.colors.secondaryGray,
                checkedBorderColor = WallXAppTheme.colors.secondary
            ),
            thumbContent = if (checked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                        tint = WallXAppTheme.colors.secondary
                    )
                }
            } else {
                null
            },
            onCheckedChange = onCheckedChange
        )
    }
}
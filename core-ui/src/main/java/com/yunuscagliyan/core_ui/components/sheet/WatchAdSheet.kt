package com.yunuscagliyan.core_ui.components.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.components.button.FilledSecondaryButton
import com.yunuscagliyan.core_ui.components.button.OutlinedSecondaryButton
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchAdSheet(
    onDismissRequest: () -> Unit,
    onWatchAdClick: () -> Unit,
    onProClick: () -> Unit,
) {
    BaseModalSheet(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = WallXAppTheme.dimension.paddingMedium1
                ),
        ) {
            Text(
                text = stringResource(id = R.string.watch_ad_sheet_title),
                style = WallXAppTheme.typography.normal1,
                color = WallXAppTheme.colors.accent,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingMedium1))
            OutlinedSecondaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.watch_ad_sheet_button_watch_video),
                onClick = onWatchAdClick,
                contentPadding= PaddingValues(
                    horizontal = WallXAppTheme.dimension.paddingMedium2,
                    vertical = WallXAppTheme.dimension.paddingMedium1
                ),
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_ad_play),
                        contentDescription = stringResource(id = R.string.watch_ad_sheet_button_watch_video),
                        tint = WallXAppTheme.colors.secondary,
                        modifier = Modifier
                            .size(WallXAppTheme.dimension.iconSizeSmall)
                    )
                }
            )
            Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingSmall3))
            FilledSecondaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.watch_ad_sheet_button_pro),
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_crown),
                        contentDescription = stringResource(id = R.string.watch_ad_sheet_button_pro),
                        tint = WallXAppTheme.colors.white,
                        modifier = Modifier
                            .size(WallXAppTheme.dimension.iconSizeSmall)
                    )
                },
                contentPadding= PaddingValues(
                    horizontal = WallXAppTheme.dimension.paddingMedium2,
                    vertical = WallXAppTheme.dimension.paddingMedium1
                ),
                onClick = onProClick
            )
            Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingMedium2))
        }
    }
}

@Preview
@Composable
private fun PreviewWatchAdSheet() {
    WatchAdSheet(
        onWatchAdClick = {},
        onProClick = {},
        onDismissRequest = {}
    )
}
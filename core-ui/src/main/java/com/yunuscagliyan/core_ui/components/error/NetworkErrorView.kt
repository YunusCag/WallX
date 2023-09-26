package com.yunuscagliyan.core_ui.components.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.components.button.FilledSecondaryButton
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun NetworkErrorView(
    modifier: Modifier = Modifier,
    message: String? = null,
    onRefreshClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = message ?: stringResource(id = R.string.common_http_error),
            modifier = Modifier
                .size(WallXAppTheme.dimension.iconSizeLarge),
            tint = WallXAppTheme.colors.error
        )
        Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingSmall2))
        Text(
            text = message ?: stringResource(id = R.string.common_http_error),
            style = WallXAppTheme.typography.normal1,
            color = WallXAppTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingSmall2))
        onRefreshClick?.let { onClick ->
            FilledSecondaryButton(
                modifier = Modifier
                    .width(160.dp),
                text = stringResource(id = R.string.common_refresh),
                onClick = onClick
            )
        }
    }
}
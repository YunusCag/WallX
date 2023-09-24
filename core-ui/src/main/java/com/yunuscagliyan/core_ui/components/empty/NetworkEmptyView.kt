package com.yunuscagliyan.core_ui.components.empty

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun NetworkEmptyView(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int = R.drawable.not_found,
    title: String = stringResource(id = R.string.common_empty_text),
    titleTextStyle: TextStyle = WallXAppTheme.typography.normal1,
    titleTextColor: Color = WallXAppTheme.colors.textPrimary
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(WallXAppTheme.dimension.collectionItemHeight),
            painter = painterResource(id = image),
            contentDescription = title,
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = titleTextStyle,
            color = titleTextColor
        )
    }
}
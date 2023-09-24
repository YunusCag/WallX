package com.yunuscagliyan.core_ui.components.card

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun SettingCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = WallXAppTheme.colors.card
        ),
        shape = WallXAppTheme.shapes.nonRound,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        content = content
    )
}
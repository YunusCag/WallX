package com.yunuscagliyan.core_ui.components.radio

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioListItem(
    title: String,
    titleStyle: TextStyle = WallXAppTheme.typography.normal2,
    titleColor: Color = WallXAppTheme.colors.textPrimary,
    selected: Boolean = false,
    selectedColor: Color = WallXAppTheme.colors.secondary,
    onClick: () -> Unit
) {
    OutlinedCard(
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Transparent
        ),
        shape = WallXAppTheme.shapes.medium,
        border = BorderStroke(
            width = WallXAppTheme.dimension.borderWidth,
            color = if (selected) selectedColor else WallXAppTheme.colors.secondaryGray
        ),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = WallXAppTheme.dimension.paddingSmall2
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = selectedColor,
                    unselectedColor = WallXAppTheme.colors.secondaryGray
                ),
            )
            Spacer(modifier = Modifier.width(WallXAppTheme.dimension.paddingSmall1))
            Text(
                text = title,
                style = titleStyle,
                color = titleColor
            )
        }
    }
}

@Preview
@Composable
fun PreviewRadioListItem() {
    RadioListItem(
        title = "Home Screen",
        selected = false
    ) {

    }

}
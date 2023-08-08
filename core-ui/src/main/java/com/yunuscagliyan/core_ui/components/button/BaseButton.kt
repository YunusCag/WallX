package com.yunuscagliyan.core_ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun FilledBaseButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.filledTonalShape,
    colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(
        containerColor = WallXAppTheme.colors.secondary
    ),
    elevation: ButtonElevation? = ButtonDefaults.filledTonalButtonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = WallXAppTheme.dimension.paddingMedium2,
        vertical = WallXAppTheme.dimension.paddingSmall2
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    FilledTonalButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content
    )

}

@Composable
fun OutlinedBaseButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.outlinedShape,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        containerColor = Color.Transparent,
        contentColor = WallXAppTheme.colors.secondary
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = ButtonDefaults.outlinedButtonBorder,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = WallXAppTheme.dimension.paddingMedium2,
        vertical = WallXAppTheme.dimension.paddingSmall2
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content
    )
}

@Composable
fun FilledSecondaryTextButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = WallXAppTheme.typography.normal1,
    textColor: Color = WallXAppTheme.colors.white,
    onClick: () -> Unit,
) {
    FilledBaseButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
fun OutlinedSecondaryTextButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = WallXAppTheme.typography.normal1,
    textColor: Color = WallXAppTheme.colors.secondary,
    onClick: () -> Unit,
) {
    OutlinedBaseButton(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(
            width = WallXAppTheme.dimension.borderWidth,
            color = WallXAppTheme.colors.secondary
        )
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Preview
@Composable
fun PreviewFilledTextButton() {
    FilledSecondaryTextButton(text = "OKAY") {

    }
}

@Preview
@Composable
fun PreviewOutlinedTextButton() {
    OutlinedSecondaryTextButton(text = "CANCEL") {

    }
}
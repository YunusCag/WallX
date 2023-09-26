package com.yunuscagliyan.core_ui.components.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.components.loading.WallDefaultLoading
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
fun FilledSecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = WallXAppTheme.typography.normal1,
    textColor: Color = WallXAppTheme.colors.white,
    icon: (@Composable () -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = WallXAppTheme.dimension.paddingMedium2,
        vertical = WallXAppTheme.dimension.paddingSmall2
    ),
    onClick: () -> Unit,
) {
    FilledBaseButton(
        modifier = modifier,
        contentPadding = contentPadding,
        onClick = onClick
    ) {
        icon?.let {
            it()
            Spacer(
                modifier = Modifier
                    .width(WallXAppTheme.dimension.paddingSmall2)
            )
        }

        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
fun FilledWhiteTextButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = WallXAppTheme.typography.normal1,
    textColor: Color = WallXAppTheme.colors.black,
    onClick: () -> Unit,
) {
    FilledBaseButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = WallXAppTheme.colors.white
        )
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

enum class LoadingButtonType {
    INIT,
    LOADING,
    SUCCESS,
    ERROR
}

@Composable
fun FilledLoadingButton(
    modifier: Modifier = Modifier,
    initialText: String,
    successText: String,
    errorText: String,
    @DrawableRes iconId: Int? = null,
    textStyle: TextStyle = WallXAppTheme.typography.normal1.copy(
        fontWeight = FontWeight.Bold
    ),
    textColor: Color = WallXAppTheme.colors.white,
    backgroundColor: Color = WallXAppTheme.colors.secondary,
    type: LoadingButtonType = LoadingButtonType.INIT,
    onClick: () -> Unit,
) {
    FilledBaseButton(
        modifier = modifier
            .height(50.dp),
        onClick = {
            if (type == LoadingButtonType.INIT) {
                onClick.invoke()
            }
        },
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = backgroundColor
        )
    ) {
        when (type) {
            LoadingButtonType.INIT -> {
                iconId?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = initialText,
                        tint = WallXAppTheme.colors.white
                    )
                    Spacer(modifier = Modifier.width(WallXAppTheme.dimension.paddingSmall1))
                }
                Text(
                    text = initialText,
                    style = textStyle,
                    color = textColor
                )
            }

            LoadingButtonType.LOADING -> {
                WallDefaultLoading(
                    modifier = Modifier
                        .size(WallXAppTheme.dimension.iconSizeSmall)
                )
            }

            LoadingButtonType.SUCCESS -> {
                Icon(
                    painterResource(id = R.drawable.ic_success),
                    contentDescription = "success",
                    tint = WallXAppTheme.colors.success,
                    modifier = Modifier
                        .size(WallXAppTheme.dimension.iconSizeSmall)
                )
                Spacer(modifier = Modifier.width(WallXAppTheme.dimension.paddingSmall1))
                Text(
                    text = successText,
                    style = textStyle,
                    color = textColor
                )
            }

            LoadingButtonType.ERROR -> {
                Icon(
                    painterResource(id = R.drawable.ic_warning),
                    contentDescription = "error",
                    tint = WallXAppTheme.colors.error,
                    modifier = Modifier
                        .size(WallXAppTheme.dimension.iconSizeSmall)
                )
                Spacer(modifier = Modifier.width(WallXAppTheme.dimension.paddingSmall1))
                Text(
                    text = errorText,
                    style = textStyle,
                    color = textColor
                )
            }
        }

    }
}

@Composable
fun OutlinedSecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = WallXAppTheme.typography.normal1,
    textColor: Color = WallXAppTheme.colors.secondary,
    icon: (@Composable () -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = WallXAppTheme.dimension.paddingMedium2,
        vertical = WallXAppTheme.dimension.paddingSmall2
    ),
    onClick: () -> Unit,
) {
    OutlinedBaseButton(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(
            width = WallXAppTheme.dimension.borderWidth,
            color = WallXAppTheme.colors.secondary
        ),
        contentPadding = contentPadding,
    ) {
        icon?.let {
            it()
            Spacer(
                modifier = Modifier
                    .width(WallXAppTheme.dimension.paddingSmall2)
            )
        }
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
    FilledSecondaryButton(text = "OKAY") {

    }
}

@Preview
@Composable
fun PreviewOutlinedTextButton() {
    OutlinedSecondaryButton(text = "CANCEL") {

    }
}
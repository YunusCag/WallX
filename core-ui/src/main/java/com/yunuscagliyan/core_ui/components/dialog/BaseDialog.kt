package com.yunuscagliyan.core_ui.components.dialog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.yunuscagliyan.core.util.Constant.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.components.button.FilledSecondaryTextButton
import com.yunuscagliyan.core_ui.extension.noRippleClickable
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun BaseDialog(
    @DrawableRes iconId: Int? = null,
    iconTint: Color = WallXAppTheme.colors.secondary,
    title: String? = null,
    description: String? = null,
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = true,
        usePlatformDefaultWidth = false,
        decorFitsSystemWindows = false
    ),
    containerColor: Color = WallXAppTheme.colors.background,
    disabledContainerColor: Color = WallXAppTheme.colors.background,
    onDismissRequest: () -> Unit,
    bottomButtons: (@Composable RowScope.() -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(
            modifier = Modifier
                .noRippleClickable {
                    if (properties.dismissOnClickOutside) {
                        onDismissRequest()
                    }
                }
                .fillMaxSize(),
            color = WallXAppTheme.colors.black.copy(
                alpha = 0.3f
            )
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                ConstraintLayout(
                    modifier = Modifier
                ) {
                    val (card, icon) = createRefs()
                    val padding = WallXAppTheme.dimension.iconSizeLarge / 2

                    Card(
                        shape = CardDefaults.outlinedShape,
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.8f)
                            .constrainAs(card) {
                                top.linkTo(parent.top, padding + 2.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = containerColor,
                            disabledContainerColor = disabledContainerColor
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = stringResource(id = R.string.common_close),
                                    tint = WallXAppTheme.colors.textPrimary,
                                    modifier = Modifier
                                        .noRippleClickable {
                                            onDismissRequest()
                                        }
                                        .padding(
                                            top = WallXAppTheme.dimension.paddingSmall2,
                                            end = WallXAppTheme.dimension.paddingSmall2
                                        )
                                        .size(WallXAppTheme.dimension.iconSizeSmall)
                                )
                            }
                            Spacer(modifier = Modifier.height(padding - (WallXAppTheme.dimension.iconSizeSmall + WallXAppTheme.dimension.paddingSmall2)))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = WallXAppTheme.dimension.paddingSmall2,
                                    ),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                title?.let { title ->
                                    Text(
                                        text = title,
                                        style = WallXAppTheme.typography.normal1,
                                        color = WallXAppTheme.colors.textPrimary,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingSmall1))
                                }

                                description?.let { description ->
                                    Text(
                                        text = description,
                                        style = WallXAppTheme.typography.normal3,
                                        color = WallXAppTheme.colors.textPrimary,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingSmall1))
                                }
                                Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingSmall1))
                                bottomButtons?.let { buttons ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        buttons()
                                    }
                                    Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingMedium1))
                                }
                            }
                        }
                    }

                    iconId?.let { id ->
                        Box(modifier = Modifier
                            .clip(CircleShape)
                            .background(WallXAppTheme.colors.background)
                            .constrainAs(icon) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(
                                2.dp
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = id),
                                contentDescription = EMPTY_STRING,
                                tint = iconTint,
                                modifier = Modifier
                                    .size(WallXAppTheme.dimension.iconSizeLarge)
                            )
                        }

                    }
                }

            }
        }
    }

}

@Composable
fun InfoDialog(
    title: String? = null,
    description: String,
    onDismissRequest: () -> Unit,
    onOkayClicked:(() -> Unit)? = null
) {
    BaseDialog(
        iconId = R.drawable.ic_info,
        iconTint = WallXAppTheme.colors.info,
        title = title,
        description = description,
        onDismissRequest = onDismissRequest
    ) {
        FilledSecondaryTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = WallXAppTheme.dimension.paddingMedium1
                ),
            text = stringResource(id = R.string.common_okay),
            onClick = onOkayClicked ?: onDismissRequest
        )
    }
}

@Composable
fun WarningDialog(
    title: String? = null,
    description: String,
    onDismissRequest: () -> Unit,
    onOkayClicked:(() -> Unit)? = null
) {
    BaseDialog(
        iconId = R.drawable.ic_warning,
        iconTint = WallXAppTheme.colors.warning,
        title = title,
        description = description,
        onDismissRequest = onDismissRequest
    ){
        FilledSecondaryTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = WallXAppTheme.dimension.paddingMedium1
                ),
            text = stringResource(id = R.string.common_okay),
            onClick = onOkayClicked ?: onDismissRequest
        )
    }
}

@Composable
fun ErrorDialog(
    title: String? = null,
    description: String,
    onDismissRequest: () -> Unit,
    onOkayClicked:(() -> Unit)? = null
) {
    BaseDialog(
        iconId = R.drawable.ic_error,
        iconTint = WallXAppTheme.colors.error,
        title = title,
        description = description,
        onDismissRequest = onDismissRequest
    ){
        FilledSecondaryTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = WallXAppTheme.dimension.paddingMedium1
                ),
            text = stringResource(id = R.string.common_okay),
            onClick = onOkayClicked ?: onDismissRequest
        )
    }
}

@Composable
fun SuccessDialog(
    title: String? = null,
    description: String,
    onDismissRequest: () -> Unit,
    onOkayClicked:(() -> Unit)? = null
) {
    BaseDialog(
        iconId = R.drawable.ic_success,
        iconTint = WallXAppTheme.colors.success,
        title = title,
        description = description,
        onDismissRequest = onDismissRequest
    ){
        FilledSecondaryTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = WallXAppTheme.dimension.paddingMedium1
                ),
            text = stringResource(id = R.string.common_okay),
            onClick = onOkayClicked ?: onDismissRequest
        )
    }
}


@Preview
@Composable
fun PreviewBaseDialog() {
    MaterialTheme {
        SuccessDialog(
            title = "Başlık",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
            onDismissRequest = {

            }
        ) {

        }
    }
}
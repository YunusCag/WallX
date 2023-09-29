package com.yunuscagliyan.core_ui.components.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.yunuscagliyan.core_ui.R
import com.yunuscagliyan.core_ui.components.button.FilledSecondaryButton
import com.yunuscagliyan.core_ui.components.button.OutlinedSecondaryButton
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@Composable
fun RateAppDialog(
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    BaseDialog(
        iconId = R.drawable.ic_question,
        iconTint = WallXAppTheme.colors.info,
        onDismissRequest = onDismissRequest,
        title = stringResource(id = R.string.rate_dialog_title),
        description = stringResource(id = R.string.rate_dialog_description)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = WallXAppTheme.dimension.paddingMedium1
                )
        ) {
            OutlinedSecondaryButton(
                modifier = Modifier
                    .weight(1f),
                text = stringResource(id = R.string.common_cancel),
                onClick = onCancelClick,
            )
            Spacer(modifier = Modifier.width(WallXAppTheme.dimension.paddingSmall2))
            FilledSecondaryButton(
                modifier = Modifier
                    .weight(1f),
                text = stringResource(id = R.string.common_okay),
                onClick = onConfirmClick
            )
        }
    }
}
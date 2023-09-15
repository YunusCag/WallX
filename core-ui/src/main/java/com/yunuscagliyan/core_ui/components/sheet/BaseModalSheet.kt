package com.yunuscagliyan.core_ui.components.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.yunuscagliyan.core_ui.components.radio.RadioListItem
import com.yunuscagliyan.core_ui.model.SelectionModel
import com.yunuscagliyan.core_ui.theme.WallXAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseModalSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    scrimColor: Color = WallXAppTheme.colors.black.copy(
        alpha = 0.1f
    ),
    backgroundColor: Color = WallXAppTheme.colors.background,
    shape: Shape = WallXAppTheme.shapes.bottomSheetShape,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        scrimColor = scrimColor,
        containerColor = backgroundColor,
        shape = shape,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = WallXAppTheme.colors.textPrimary.copy(
                    alpha = 0.4f
                )
            )
        },
        content = content
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleSelectionBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    titleTextStyle: TextStyle = WallXAppTheme.typography.title2,
    titleColor: Color = WallXAppTheme.colors.textPrimary,
    sheetState: SheetState = rememberModalBottomSheetState(),
    selections: List<SelectionModel>,
    selectedIndex: Int = 0,
    onDismissRequest: () -> Unit,
    onSelect: (Int, SelectionModel) -> Unit,
) {
    BaseModalSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Text(
            text = title,
            style = titleTextStyle,
            color = titleColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = WallXAppTheme.dimension.paddingMedium1
                ),
            textAlign = TextAlign.Center
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                WallXAppTheme.dimension.paddingMedium1
            ),
            verticalArrangement = Arrangement.spacedBy(WallXAppTheme.dimension.paddingSmall2)
        ) {
            items(selections.size) { index ->
                val selection = selections[index]
                RadioListItem(
                    title = selection.title,
                    selected = selectedIndex == index
                ) {
                    onSelect(index, selection)
                }
            }
        }
        Spacer(modifier = Modifier.height(WallXAppTheme.dimension.paddingMedium1))
    }
}
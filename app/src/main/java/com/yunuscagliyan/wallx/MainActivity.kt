package com.yunuscagliyan.wallx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.core_ui.components.button.FilledSecondaryTextButton
import com.yunuscagliyan.core_ui.components.button.OutlinedSecondaryTextButton
import com.yunuscagliyan.core_ui.components.dialog.ErrorDialog
import com.yunuscagliyan.core_ui.components.dialog.InfoDialog
import com.yunuscagliyan.core_ui.components.dialog.SuccessDialog
import com.yunuscagliyan.core_ui.components.dialog.WarningDialog
import com.yunuscagliyan.core_ui.components.main.MainUIFrame
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallXAppTheme {
                var showSuccessDialog by remember { mutableStateOf(false) }
                var showErrorDialog by remember { mutableStateOf(false) }
                var showWarningDialog by remember { mutableStateOf(false) }
                var showInfoDialog by remember { mutableStateOf(false) }

                var showSheet by remember { mutableStateOf(false) }
                val sheetState = rememberModalBottomSheetState()
                val scope = rememberCoroutineScope()

                if (showSheet) {
                    ModalBottomSheet(
                        sheetState = sheetState,
                        onDismissRequest = { showSheet = false },
                        shape = WallXAppTheme.shapes.bottomSheetShape,

                        ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color.Red)
                                    .fillMaxWidth()
                                    .height(250.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                FilledSecondaryTextButton(
                                    text = "Expand Sheet",
                                    onClick = {
                                        scope.launch {
                                            sheetState.expand()
                                        }
                                    }
                                )

                            }
                        }
                    }
                }
                MainUIFrame(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "WallX") }
                        )
                    },
                    bottomBar = {
                        NavigationBar {

                        }
                    }
                ) {
                    if (showSuccessDialog) {
                        SuccessDialog(
                            title = "Başlık 1",
                            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                            onDismissRequest = {
                                showSuccessDialog = false
                            },
                            onOkayClicked = {
                                showSuccessDialog = false
                            }
                        )
                    }

                    if (showErrorDialog) {
                        ErrorDialog(
                            title = "Başlık 1",
                            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                            onDismissRequest = {
                                showErrorDialog = false
                            },
                            onOkayClicked = {
                                showErrorDialog = false
                            }
                        )
                    }

                    if (showWarningDialog) {
                        WarningDialog(
                            title = "Başlık 1",
                            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                            onDismissRequest = {
                                showWarningDialog = false
                            },
                            onOkayClicked = {
                                showWarningDialog = false
                            }
                        )
                    }

                    if (showInfoDialog) {
                        InfoDialog(
                            title = "Başlık 1",
                            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
                            onDismissRequest = {
                                showInfoDialog = false
                            },
                            onOkayClicked = {
                                showInfoDialog = false
                            }
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        TextStyleView(
                            name = "Title1",
                            style = WallXAppTheme.typography.title1
                        )
                        TextStyleView(
                            name = "Title2",
                            style = WallXAppTheme.typography.title2
                        )
                        TextStyleView(
                            name = "Normal1",
                            style = WallXAppTheme.typography.normal1
                        )
                        TextStyleView(
                            name = "Normal2",
                            style = WallXAppTheme.typography.normal2
                        )
                        TextStyleView(
                            name = "Normal3",
                            style = WallXAppTheme.typography.normal3
                        )
                        TextStyleView(
                            name = "Small1-Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                            style = WallXAppTheme.typography.small1
                        )
                        TextStyleView(
                            name = "Small2-Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                            style = WallXAppTheme.typography.small2
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        FilledSecondaryTextButton(
                            text = "Show Success Dialog",
                            onClick = { showSuccessDialog = true }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedSecondaryTextButton(
                            text = "Show Error Dialog",
                            onClick = { showErrorDialog = true }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FilledSecondaryTextButton(
                            text = "Show Warning Dialog",
                            onClick = { showWarningDialog = true }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedSecondaryTextButton(
                            text = "Show Info Dialog",
                            onClick = { showInfoDialog = true }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FilledSecondaryTextButton(
                            text = "Show Sheet",
                            onClick = { showSheet = true }
                        )

                    }

                }
            }
        }
    }
}

@Composable
fun TextStyleView(name: String, style: TextStyle) {
    Text(
        text = "$name",
        style = style,
        color = WallXAppTheme.colors.textPrimary
    )
}
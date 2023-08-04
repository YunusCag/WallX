package com.yunuscagliyan.wallx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core_ui.theme.WallXAppTheme
import com.yunuscagliyan.core_ui.theme.WallXAppThemeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallXAppThemeTheme {
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
                        name = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                        style = WallXAppTheme.typography.small1
                    )
                    TextStyleView(
                        name = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                        style = WallXAppTheme.typography.small2
                    )
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
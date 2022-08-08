package com.achmad.baseandroid.theme.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.achmad.baseandroid.theme.BaseComposeTheme

@Preview
@Composable
fun BaseToolbar(
    title: String = "",
    showLeftButton: Boolean = true,
    showRightButton: Boolean = false,
    leftButtonImage: ImageVector = Icons.Filled.ArrowBack,
    rightButtonImage: ImageVector = Icons.Filled.Settings,
    onLeftButtonClick: () -> Unit = {},
    onRightButtonClick: () -> Unit = {},
    elevation: Dp = 4.dp
) {
    BaseComposeTheme {
        TopAppBar(
            title = {
                Text(
                    text = title
                )
            },
            navigationIcon = if (showLeftButton) {
                {
                    IconButton(onClick = onLeftButtonClick) {
                        Icon(
                            imageVector = leftButtonImage,
                            contentDescription = "",
                        )
                    }
                }
            } else {
                null
            },
            actions = {
                if (showRightButton) {
                    IconButton(onClick = onRightButtonClick) {
                        Icon(
                            imageVector = rightButtonImage,
                            contentDescription = "",
                        )
                    }
                }
            },
            backgroundColor = MaterialTheme.colors.primarySurface,
            elevation = elevation
        )
    }
}

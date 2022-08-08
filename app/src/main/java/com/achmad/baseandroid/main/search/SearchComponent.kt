package com.achmad.baseandroid.main.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.achmad.feature.github.data.model.User

val mockUser = User(
    username = "achmadfachrudin",
    name = "",
    bio = "",
    email = "",
    location = "",
    followers = 0,
    following = 0,
    userUrl = "",
    avatarUrl = "https://avatars.githubusercontent.com/u/19408400?v=4",
    repoUrl = ""
)

@Preview
@Composable
fun RowUser(
    model: User = mockUser,
    onItemClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onItemClick)
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model.avatarUrl),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.sp
            ),
            text = model.username
        )
    }
}

@Composable
fun MessageLabel(message: String) {
    Text(
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.sp
        ),
        text = message
    )
}

package com.achmad.baseandroid.main.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.achmad.baseandroid.R
import com.achmad.baseandroid.theme.component.annotatedHtmlString
import com.achmad.feature.github.data.model.Repository
import com.achmad.feature.github.data.model.User

@Composable
fun DetailSection(
    model: User
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model.avatarUrl),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Text(
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                text = model.name
            )

            Text(
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                ),
                text = "@${model.username}"
            )

            if (model.bio.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    text = model.bio
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {
                Icon(painterResource(id = R.drawable.ic_follower), "")

                Text(
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    text = annotatedHtmlString(
                        text = stringResource(
                            id = R.string.user_detail_follower,
                            model.followers,
                            model.following
                        )
                    )
                )
            }

            if (model.location.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Icon(painterResource(id = R.drawable.ic_location), "")

                    Text(
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        ),
                        text = model.location
                    )
                }
            }

            if (model.email.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Icon(painterResource(id = R.drawable.ic_email), "")

                    Text(
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        ),
                        text = model.email
                    )
                }
            }
        }
    }
}

@Composable
fun RowRepository(
    avatarUrl: String,
    model: Repository
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(avatarUrl),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
        ) {
            Text(
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                text = model.name
            )

            if (model.description.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    text = model.description
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {
                Icon(painterResource(id = R.drawable.ic_star), "")

                Text(
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
                    text = model.stargazersCount.toString()
                )

                Text(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
                    text = model.updatedAt
                )
            }
        }
    }
}

package com.achmad.baseandroid.main.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.achmad.baseandroid.main.MessageLabel

@Composable
fun UserScreen(
    viewModel: UserViewModel
) {
    val state by viewModel.state.observeAsState(UserViewModel.State())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
    ) {
        when (state.displayStateUser) {
            UserViewModel.State.DisplayState.Content -> {
                state.user?.let {
                    DetailSection(model = it)
                }
            }
            UserViewModel.State.DisplayState.Empty,
            is UserViewModel.State.DisplayState.Error -> {
                MessageLabel(message = "Oops, something wrong")
            }
            UserViewModel.State.DisplayState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(
                        CenterHorizontally
                    )
                )
            }
        }

        Divider()

        when (state.displayStateRepository) {
            UserViewModel.State.DisplayState.Content -> {
                LazyColumn {
                    items(state.repositories) { model ->
                        RowRepository(
                            state.user?.avatarUrl.orEmpty(),
                            model
                        )
                        Divider()
                    }
                }
            }
            UserViewModel.State.DisplayState.Empty -> {
                MessageLabel(message = "No repository")
            }
            is UserViewModel.State.DisplayState.Error -> {
                MessageLabel(message = "Oops, something wrong")
            }
            UserViewModel.State.DisplayState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(
                        CenterHorizontally
                    )
                )
            }
        }
    }
}

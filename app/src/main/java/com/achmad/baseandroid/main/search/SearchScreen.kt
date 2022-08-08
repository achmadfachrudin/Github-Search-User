package com.achmad.baseandroid.main.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onSearchChanged: (query: String) -> Unit = {},
    onLoadMore: () -> Unit = {},
    onItemClick: (username: String) -> Unit
) {
    val state by viewModel.state.observeAsState(SearchViewModel.State())
    val focusManager = LocalFocusManager.current

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
        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
            value = state.query,
            onValueChange = {
                onSearchChanged(it)
            },
            label = { Text("Enter username") },
            maxLines = 1,
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )

        when (state.displayState) {
            SearchViewModel.State.DisplayState.Content -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LazyColumn {
                        items(state.displayItems) { model ->
                            RowUser(model) { onItemClick(model.username) }
                        }

                        if (state.showLoadMore) {
                            item {
                                LinearProgressIndicator(
                                    modifier = Modifier.fillMaxWidth()
                                )
                                onLoadMore()
                            }
                        }
                    }
                }
            }
            SearchViewModel.State.DisplayState.Empty -> {
                MessageLabel(message = "${state.query} not found")
            }
            SearchViewModel.State.DisplayState.Error -> {
                MessageLabel(message = "Oops, something wrong")
            }
            SearchViewModel.State.DisplayState.Loading -> {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

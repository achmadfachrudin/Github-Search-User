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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.achmad.baseandroid.R
import com.achmad.baseandroid.main.MessageLabel

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
            .testTag("search_page")
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .testTag("tag_text_field"),
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
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = ""
                )
            }
        )

        when (state.displayState) {
            SearchViewModel.State.DisplayState.Content -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        Modifier.testTag("tag_lazy_column")
                    ) {
                        items(state.displayItems) { model ->
                            RowUser(model) { onItemClick(model.username) }
                            Divider()
                        }

                        if (state.showLoadMoreLoading) {
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
                MessageLabel(message = "User ${state.query} not found")
            }
            SearchViewModel.State.DisplayState.Error -> {
                MessageLabel(message = "Oops, something wrong")
            }
            SearchViewModel.State.DisplayState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    )
                )
            }
        }
    }
}

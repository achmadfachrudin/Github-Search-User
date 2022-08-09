package com.achmad.baseandroid.main.search

import com.achmad.common.ApiResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchLoadMoreTest : SearchViewModelTest() {

    @Test
    fun `onLoadMore should add item and page increase`() {
        coroutinesRule.runTest {
            viewModel.mockState {
                copy(
                    displayItems = mutableListOf(
                        user.copy("achmad 1")
                    ),
                    query = "achmad",
                    page = 1
                )
            }

            stubSearchUserList(
                query = "achmad",
                page = 2,
                result = flowOf(
                    ApiResult.Success(
                        listOf(
                            user.copy("achmad 2")
                        )
                    )
                )
            )

            viewModel.onIntentReceived(
                SearchViewModel.Intent.LoadMore
            )
            runCurrent()

            Assert.assertEquals(
                SearchViewModel.State.DisplayState.Content,
                observedStateList.last().displayState
            )

            Assert.assertEquals(
                2,
                observedStateList.last().displayItems.size
            )

            Assert.assertEquals(
                2,
                observedStateList.last().page
            )
        }
    }
}

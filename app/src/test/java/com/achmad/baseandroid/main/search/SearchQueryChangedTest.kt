package com.achmad.baseandroid.main.search

import com.achmad.common.ApiResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchQueryChangedTest : SearchViewModelTest() {

    @Test
    fun `onSearchChanged with empty query should not show anything`() {
        coroutinesRule.runTest {
            viewModel.onIntentReceived(
                SearchViewModel.Intent.QueryChanged("")
            )
            runCurrent()

            Assert.assertEquals(
                SearchViewModel.State.DisplayState.Content,
                observedStateList.last().displayState
            )

            Assert.assertEquals(
                0,
                observedStateList.last().displayItems.size
            )
        }
    }

    @Test
    fun `onSearchChanged with query but no result should show empty`() {
        coroutinesRule.runTest {
            stubSearchUserList(
                query = "achmad",
                result = flowOf(ApiResult.Success(emptyList()))
            )

            viewModel.onIntentReceived(
                SearchViewModel.Intent.QueryChanged("achmad")
            )
            runCurrent()
            delay(1000)

            Assert.assertEquals(
                SearchViewModel.State.DisplayState.Empty,
                observedStateList.last().displayState
            )

            Assert.assertEquals(
                0,
                observedStateList.last().displayItems.size
            )
        }
    }

    @Test
    fun `onSearchChanged with query and have result should show user list`() {
        coroutinesRule.runTest {
            stubSearchUserList(
                query = "achmad",
                result = flowOf(
                    ApiResult.Success(
                        listOf(
                            user.copy("achmad")
                        )
                    )
                )
            )

            viewModel.onIntentReceived(
                SearchViewModel.Intent.QueryChanged("achmad")
            )
            runCurrent()

            Assert.assertEquals(
                SearchViewModel.State.DisplayState.Content,
                observedStateList.last().displayState
            )

            Assert.assertEquals(
                1,
                observedStateList.last().displayItems.size
            )
        }
    }

    @Test
    fun `onViewCreated failed should show error`() {
        coroutinesRule.runTest {
            stubSearchUserList(
                query = "achmad",
                result = flowOf(
                    ApiResult.Error(
                        "error"
                    )
                )
            )

            viewModel.onIntentReceived(
                SearchViewModel.Intent.QueryChanged("achmad")
            )
            runCurrent()

            Assert.assertEquals(
                SearchViewModel.State.DisplayState.Error,
                observedStateList.last().displayState
            )
        }
    }
}

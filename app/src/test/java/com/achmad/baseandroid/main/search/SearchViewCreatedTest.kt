package com.achmad.baseandroid.main.search

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewCreatedTest : SearchViewModelTest() {

    @Test
    fun `onViewCreated with empty query should not show anything`() {
        coroutinesRule.runTest {
            viewModel.onIntentReceived(
                SearchViewModel.Intent.ViewCreated
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
}

package com.achmad.baseandroid.main.user

import com.achmad.common.ApiResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewCreatedTest : UserViewModelTest() {

    @Test
    fun `onViewCreated with user & repository success should show content`() {
        coroutinesRule.runTest {
            stubUser(
                username = username,
                result = flowOf(ApiResult.Success(user.copy(username = username)))
            )

            stubRepository(
                username = username,
                result = flowOf(ApiResult.Success(listOf(userRepository)))
            )

            viewModel.onIntentReceived(
                UserViewModel.Intent.ViewCreated(username)
            )
            runCurrent()

            Assert.assertEquals(
                UserViewModel.State.DisplayState.Content,
                observedStateList.last().displayStateUser
            )

            Assert.assertEquals(
                username,
                observedStateList.last().user?.username
            )

            Assert.assertEquals(
                UserViewModel.State.DisplayState.Content,
                observedStateList.last().displayStateRepository
            )

            Assert.assertEquals(
                1,
                observedStateList.last().repositories.size
            )
        }
    }

    @Test
    fun `onViewCreated with user & repository failed should show error`() {
        coroutinesRule.runTest {
            stubUser(
                username = username,
                result = flowOf(ApiResult.Error(""))
            )

            stubRepository(
                username = username,
                result = flowOf(ApiResult.Error(""))
            )

            viewModel.onIntentReceived(
                UserViewModel.Intent.ViewCreated(username)
            )
            runCurrent()

            Assert.assertEquals(
                UserViewModel.State.DisplayState.Error(""),
                observedStateList.last().displayStateUser
            )

            Assert.assertEquals(
                UserViewModel.State.DisplayState.Error(""),
                observedStateList.last().displayStateRepository
            )
        }
    }
}

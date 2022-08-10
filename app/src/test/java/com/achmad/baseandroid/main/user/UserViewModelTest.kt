package com.achmad.baseandroid.main.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.achmad.baseandroid.CoroutineRule
import com.achmad.baseandroid.service.GithubRepository
import com.achmad.common.ApiResult
import com.achmad.feature.github.data.model.Repository
import com.achmad.feature.github.data.model.User
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
open class UserViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    protected val repository = mockk<GithubRepository>()

    protected val viewModel = UserViewModel(
        repository
    )

    protected val observedStateList = mutableListOf<UserViewModel.State>()
    protected val observedEffectList = mutableListOf<UserViewModel.Effect>()

    private val observerState = mockk<Observer<UserViewModel.State>>()
    private val slotState = slot<UserViewModel.State>()

    @Before
    fun setup() {
        clearAllMocks()

        viewModel.state.observeForever(observerState)

        every {
            observerState.onChanged(capture(slotState))
        } answers {
            observedStateList.add(slotState.captured)
        }

        coroutinesRule.testScope.launch(UnconfinedTestDispatcher()) {
            viewModel.effect.collect { event ->
                event?.peekContent()?.let {
                    observedEffectList.add(it)
                }
            }
        }
    }

    @After
    fun tearDown() {
        observedStateList.clear()
        observedEffectList.clear()

        viewModel.state.removeObserver(observerState)
    }

    protected fun stubUser(
        username: String,
        page: Int = 1,
        result: Flow<ApiResult<User>>
    ) {
        coEvery {
            repository.fetchUser(username)
        } returns result
    }

    protected fun stubRepository(
        username: String,
        result: Flow<ApiResult<List<Repository>>>
    ) {
        coEvery {
            repository.fetchRepository(username)
        } returns result
    }

    protected val user = User(
        username = "",
        name = "",
        bio = "",
        email = "",
        location = "",
        followers = 0,
        following = 0,
        userUrl = "",
        avatarUrl = "",
        repoUrl = ""
    )

    protected val userRepository = Repository(
        name = "",
        description = "",
        stargazersCount = 0,
        updatedAt = ""
    )

    protected val username = "achmadfachrudin"
}

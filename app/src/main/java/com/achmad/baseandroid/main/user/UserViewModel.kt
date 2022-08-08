package com.achmad.baseandroid.main.user

import androidx.lifecycle.viewModelScope
import com.achmad.baseandroid.base.BaseViewModel
import com.achmad.baseandroid.service.GithubRepository
import com.achmad.common.ApiResult
import com.achmad.feature.github.data.model.Repository
import com.achmad.feature.github.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : BaseViewModel<UserViewModel.Intent,
    UserViewModel.State,
    UserViewModel.Effect>(
    State()
) {

    sealed class Intent {
        data class ViewCreated(val username: String) : Intent()
    }

    data class State(
        var username: String = "",
        var user: User? = null,
        val repositories: MutableList<Repository> = mutableListOf(),
        val displayStateUser: DisplayState = DisplayState.Loading,
        val displayStateRepository: DisplayState = DisplayState.Loading
    ) {
        sealed class DisplayState {
            data class Error(val message: String) : DisplayState()
            object Loading : DisplayState()
            object Content : DisplayState()
            object Empty : DisplayState()
        }
    }

    sealed class Effect

    override fun onIntentReceived(intent: Intent) {
        when (intent) {
            is Intent.ViewCreated -> onViewCreated(intent.username)
        }
    }

    private fun onViewCreated(username: String) {
        fetchUser(username)
        fetchRepository(username)
    }

    private fun fetchUser(username: String) {
        viewModelScope.launch {
            githubRepository.fetchUser(username).collectLatest { result ->
                when (result) {
                    ApiResult.Loading -> {
                        setState { copy(displayStateUser = State.DisplayState.Loading) }
                    }
                    is ApiResult.Error -> {
                        setState {
                            copy(
                                displayStateUser = State.DisplayState.Error(result.error.orEmpty())
                            )
                        }
                    }
                    is ApiResult.Success -> {
                        setState {
                            copy(
                                displayStateUser = State.DisplayState.Content,
                                user = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    private fun fetchRepository(username: String) {
        viewModelScope.launch {
            githubRepository.fetchRepository(username).collectLatest { result ->
                when (result) {
                    ApiResult.Loading -> {
                        setState { copy(displayStateRepository = State.DisplayState.Loading) }
                    }
                    is ApiResult.Error -> {
                        setState {
                            copy(
                                displayStateRepository = State.DisplayState.Error(result.error.orEmpty())
                            )
                        }
                    }
                    is ApiResult.Success -> {
                        if (result.data.isNullOrEmpty()) {
                            setState { copy(displayStateRepository = State.DisplayState.Empty) }
                        } else {
                            setState {
                                copy(
                                    displayStateRepository = State.DisplayState.Content,
                                    repositories = result.data.orEmpty().toMutableList()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

package com.achmad.baseandroid.main

import androidx.lifecycle.viewModelScope
import com.achmad.baseandroid.base.BaseViewModel
import com.achmad.baseandroid.service.GithubRepository
import com.achmad.common.ApiResult
import com.achmad.feature.github.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : BaseViewModel<MainViewModel.Intent,
    MainViewModel.State,
    MainViewModel.Effect>(
    State()
) {

    sealed class Intent {
        object ViewCreated : Intent()
        data class SearchChanged(val query: String) : Intent()
        object LoadMore : Intent()
    }

    data class State(
        var query: String = "",
        var page: Int = 1,
        val displayItems: MutableList<User> = mutableListOf(),
        val displayState: DisplayState = DisplayState.Loading,
        val showLoadMore: Boolean = false
    ) {
        sealed class DisplayState {
            object Error : DisplayState()
            object Loading : DisplayState()
            object Content : DisplayState()
            object Empty : DisplayState()
        }
    }

    sealed class Effect {
        data class ShowToastMessage(val query: String) : Effect()
    }

    override fun onIntentReceived(intent: Intent) {
        when (intent) {
            is Intent.ViewCreated -> searchUser()
            is Intent.SearchChanged -> onSearchChanged(intent.query)
            is Intent.LoadMore -> onLoadMore()
        }
    }

    private fun onSearchChanged(query: String) {
        setState { copy(query = query) }
        Timer().schedule(500) {
            searchUser()
        }
    }

    private fun searchUser(isLoadMore: Boolean = false) {
        viewModelScope.launch {
            val query = viewState.query
            val page = viewState.page

            setState { copy(displayState = State.DisplayState.Loading) }

            if (query.isNotEmpty()) {
                githubRepository.searchUserList(query, page).collectLatest { result ->
                    when (result) {
                        ApiResult.Loading -> {
                            if (isLoadMore) {
                                setState { copy(showLoadMore = true) }
                            } else {
                                setState { copy(displayState = State.DisplayState.Loading) }
                            }
                        }
                        is ApiResult.Error -> {
                            if (isLoadMore) {
                                setState { copy(showLoadMore = false) }
                                setEffect(Effect.ShowToastMessage(result.error.orEmpty()))
                            } else {
                                setState { copy(displayState = State.DisplayState.Error) }
                            }
                        }
                        is ApiResult.Success -> {
                            val newItems = viewState.displayItems
                            newItems.addAll(result.data.orEmpty())

                            if (result.data.isNullOrEmpty()) {
                                setState { copy(displayState = State.DisplayState.Empty) }
                            } else {
                                setState {
                                    copy(
                                        displayState = State.DisplayState.Content,
                                        displayItems = newItems,
                                        showLoadMore = false
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                setState {
                    copy(
                        displayState = State.DisplayState.Content,
                        displayItems = mutableListOf()
                    )
                }
            }
        }
    }

    private fun onLoadMore() {
        setState { copy(page = viewState.page.plus(1)) }
        searchUser(isLoadMore = true)
    }
}

package com.achmad.baseandroid.main.search

import androidx.lifecycle.viewModelScope
import com.achmad.baseandroid.core.base.BaseViewModel
import com.achmad.baseandroid.service.GithubRepository
import com.achmad.common.ApiResult
import com.achmad.feature.github.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : BaseViewModel<SearchViewModel.Intent,
    SearchViewModel.State,
    SearchViewModel.Effect>(
    State()
) {

    sealed class Intent {
        object ViewCreated : Intent()
        data class QueryChanged(val query: String) : Intent()
        object LoadMore : Intent()
    }

    data class State(
        var query: String = "",
        var page: Int = 1,
        val displayItems: MutableList<User> = mutableListOf(),
        val displayState: DisplayState = DisplayState.Loading,
        val showLoadMoreLoading: Boolean = false
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
            is Intent.QueryChanged -> onSearchChanged(intent.query)
            is Intent.LoadMore -> onLoadMore()
        }
    }

    private fun onSearchChanged(query: String) {
        setState { copy(query = query, displayItems = mutableListOf()) }
        searchUser()
    }

    private fun searchUser(isLoadMore: Boolean = false) {
        viewModelScope.launch {
            val query = viewState.query
            val page = viewState.page

            if (query.isNotEmpty()) {
                githubRepository.searchUserList(query, page).collectLatest { result ->
                    when (result) {
                        ApiResult.Loading -> {
                            if (isLoadMore) {
                                setState { copy(showLoadMoreLoading = true) }
                            } else {
                                setState { copy(displayState = State.DisplayState.Loading) }
                            }
                        }
                        is ApiResult.Error -> {
                            if (isLoadMore) {
                                setState { copy(showLoadMoreLoading = false) }
                            } else {
                                setState { copy(displayState = State.DisplayState.Error) }
                            }
                            setEffect(Effect.ShowToastMessage(result.error.orEmpty()))
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
                                        showLoadMoreLoading = false
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

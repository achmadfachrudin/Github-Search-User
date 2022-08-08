package com.achmad.baseandroid.main.search

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import com.achmad.baseandroid.main.user.UserActivity
import com.achmad.baseandroid.theme.BaseColor
import com.achmad.baseandroid.theme.BaseComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onIntentReceived(SearchViewModel.Intent.ViewCreated)

        setContent {
            BaseComposeTheme {
                Surface(color = BaseColor.White) {
                    SearchScreen(
                        viewModel = viewModel,
                        onSearchChanged = {
                            viewModel.onIntentReceived(SearchViewModel.Intent.SearchChanged(it))
                        },
                        onLoadMore = {
                            viewModel.onIntentReceived(SearchViewModel.Intent.LoadMore)
                        },
                        onItemClick = { goToUserDetail(it) }
                    )
                }
            }
        }
    }

    private fun goToUserDetail(username: String) {
        startActivity(
            UserActivity.createIntent(
                this,
                username
            )
        )
    }
}

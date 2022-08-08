package com.achmad.baseandroid.main.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import com.achmad.baseandroid.theme.BaseColor
import com.achmad.baseandroid.theme.BaseComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {

    companion object {
        private const val BUNDLE_USERNAME = "BUNDLE_USERNAME"

        fun createIntent(
            context: Context,
            username: String
        ): Intent {
            return Intent(context, UserActivity::class.java).apply {
                putExtra(BUNDLE_USERNAME, username)
            }
        }
    }

    private val username by lazy { intent.getStringExtra(BUNDLE_USERNAME).orEmpty() }

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onIntentReceived(UserViewModel.Intent.ViewCreated(username))

        setContent {
            BaseComposeTheme {
                Surface(color = BaseColor.White) {
                    UserScreen(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

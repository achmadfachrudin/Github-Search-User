package com.achmad.baseandroid.main.user

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import com.achmad.baseandroid.createAndroidIntentComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UserActivityTest {

    private val username = "achmadfachrudin"

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidIntentComposeRule<UserActivity> {
        UserActivity.createIntent(it, username)
    }

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun pageOpened() {
        with(composeTestRule) {
            onNodeWithTag("user_page")
                .assertIsDisplayed()
        }
    }

    @Test
    fun usernameIsDisplayed() {
        with(composeTestRule) {
            waitUntil(1000L) {
                onAllNodesWithTag("tag_text_username")
                    .fetchSemanticsNodes().size == 1
            }

            onNodeWithTag("tag_text_username")
                .assertIsDisplayed()
                .assert(hasText("@$username"))
        }
    }

    @Test
    fun repositoryIsDisplayed() {
        with(composeTestRule) {
            waitUntil(1000L) {
                onAllNodesWithTag("tag_lazy_column_repositories")
                    .fetchSemanticsNodes().size == 1
            }

            onNodeWithTag("tag_lazy_column_repositories")
                .assertIsDisplayed()
        }
    }
}

package com.achmad.baseandroid.main.search

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SearchActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<SearchActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun pageOpened() {
        with(composeTestRule) {
            onNodeWithTag("search_page")
                .assertIsDisplayed()
        }
    }

    @Test
    fun searchUserAndNotFound() {
        with(composeTestRule) {
            onNodeWithTag("tag_text_field")
                .assertIsDisplayed()

            onNodeWithTag("tag_text_field")
                .performTextInput("9876zxc")

            waitUntil(1000L) {
                onAllNodesWithTag("message_label")
                    .fetchSemanticsNodes().size == 1
            }

            onNodeWithTag("message_label")
                .assert(hasText("User 9876zxc not found"))
        }
    }

    @Test
    fun searchUserAndClicked() {
        with(composeTestRule) {
            onNodeWithTag("tag_text_field")
                .assertIsDisplayed()

            onNodeWithTag("tag_text_field")
                .performTextInput("achmadfachrudin")

            onNodeWithTag("tag_text_field")
                .assert(hasText("achmadfachrudin"))

            waitUntil(1000L) {
                onAllNodesWithTag("tag_lazy_column")
                    .fetchSemanticsNodes().size == 1
            }

            onNodeWithTag("tag_lazy_column")
                .onChildAt(0)
                .performClick()

            waitUntil(1000L) {
                onAllNodesWithTag("user_page")
                    .fetchSemanticsNodes().size == 1
            }
        }
    }
}

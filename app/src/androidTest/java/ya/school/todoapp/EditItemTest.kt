package ya.school.todoapp

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextReplacement
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import ya.school.todoapp.presentation.MainActivity

@HiltAndroidTest
class EditItemTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun clickOnAddItemButton() {
        val randomText = "random text"
        val anotherText = "another text"

        with (composeTestRule) {
            waitForIdle()
            onNodeWithTag("add_item_btn").performClick()
            waitForIdle()
            onNodeWithTag("text_input").performTextReplacement(randomText)
            onNodeWithTag("save_btn").performClick()
            waitForIdle()
            onNodeWithTag("todo_list")
                .performScrollToNode(hasText(randomText))
            onNodeWithText(randomText).performClick()
            waitForIdle()
            onNodeWithTag("text_input").performTextReplacement(anotherText)
            onNodeWithTag("save_btn").performClick()
            waitForIdle()
            onNodeWithTag("todo_list")
                .performScrollToNode(hasText(anotherText))
            onNodeWithText(anotherText).assertExists()
            onNodeWithText(randomText).assertDoesNotExist()
        }
    }
}
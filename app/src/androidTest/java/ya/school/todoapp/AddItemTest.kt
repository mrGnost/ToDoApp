package ya.school.todoapp

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextReplacement
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import ya.school.todoapp.presentation.MainActivity

@HiltAndroidTest
class AddItemTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun clickOnAddItemButton() {
        val randomText = "random text"

        with (composeTestRule) {
            waitForIdle()
            val itemsCount = onAllNodes(hasTestTag("todo_item"))
                .fetchSemanticsNodes()
                .size
            onNodeWithTag("add_item_btn").performClick()
            waitForIdle()
            onNodeWithTag("text_input").performTextReplacement(randomText)
            onNodeWithTag("save_btn").performClick()
            waitForIdle()
            val newItemCount = onAllNodes(hasTestTag("todo_item"))
                .fetchSemanticsNodes()
                .size
            assertEquals(newItemCount, itemsCount + 1)
            onNodeWithTag("todo_list")
                .performScrollToNode(hasText(randomText))
            onNodeWithText(randomText).assertExists()
        }
    }
}
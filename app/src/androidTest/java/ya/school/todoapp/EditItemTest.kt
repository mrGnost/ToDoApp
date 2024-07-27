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
class EditItemTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun clickOnAddItemButton() {
        val randomText = "random text"
        val anotherText = "another text"

        composeTestRule.waitForIdle()
        val itemsCount = composeTestRule
            .onAllNodes(hasTestTag("todo_item"))
            .fetchSemanticsNodes()
            .size
        composeTestRule.onNodeWithTag("add_item_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("text_input").performTextReplacement(randomText)
        composeTestRule.onNodeWithTag("save_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("todo_list")
            .performScrollToNode(hasText(randomText))
        composeTestRule.onNodeWithText(randomText).performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("text_input").performTextReplacement(anotherText)
        composeTestRule.onNodeWithTag("save_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("todo_list")
            .performScrollToNode(hasText(anotherText))
        composeTestRule.onNodeWithText(anotherText).assertExists()
        composeTestRule.onNodeWithText(randomText).assertDoesNotExist()
    }
}
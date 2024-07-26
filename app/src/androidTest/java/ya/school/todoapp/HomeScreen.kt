package ya.school.todoapp

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule

class HomeScreen {
    @get:Rule val composeRule = createComposeRule()

    val completeVisibleButton = composeRule.onNodeWithTag("complete_visible_button")

    class TodoItem(private val matcher: SemanticsMatcher)
}
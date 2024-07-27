package ya.school.todoapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ya.school.todoapp.presentation.ui.home.HomeScreen
import ya.school.todoapp.presentation.ui.navigation.ToDoNavigation
import ya.school.todoapp.presentation.ui.navigation.TodoNavGraph
import ya.school.todoapp.presentation.ui.task.TaskFormScreen

class AddItemTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            TestNavGraph(navigator = ToDoNavigation(navController))
        }
    }

    @Test
    fun addItemTest() {
        composeTestRule.onNodeWithTag("add_item_btn").performClick()

    }
}
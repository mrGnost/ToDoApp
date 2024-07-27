package ya.school.todoapp

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ya.school.todoapp.presentation.ui.home.HomeScreen
import ya.school.todoapp.presentation.ui.navigation.ToDoNavigation
import ya.school.todoapp.presentation.ui.task.TaskFormScreen

@Composable
fun TestNavGraph(navigator: ToDoNavigation) {
    NavHost(
        navController = navigator.navController,
        startDestination = "home"
    ) {
        composable(ToDoNavigation.HOME_ROUTE) {
            HomeScreen(
                navigator = navigator,
                viewModel = ViewModelMockUtil.getHomeViewModel()
            )
        }
        composable(ToDoNavigation.TASK_ROUTE) {
            TaskFormScreen(
                navigator = navigator,
                viewModel = ViewModelMockUtil.getTaskViewModel(),
                taskId = null
            )
        }
    }
}
package ya.school.todoapp.presentation.ui

import androidx.navigation.NavHostController

/**
 * Класс, отвечающий за навигацию между экранами
 */
class ToDoNavigation(val navController: NavHostController) {
    fun navigateToHome() =
        navController.navigate(HOME_ROUTE) {
            popUpTo(navController.currentBackStackEntry?.destination?.route ?: return@navigate) {
                inclusive = true
            }
        }

    fun navigateToTask(taskId: String) =
        navController.navigate("$TASK_ROUTE/$taskId")

    fun navigateToTaskForm() =
        navController.navigate(TASK_ROUTE)

    fun navigateToInfo() =
        navController.navigate(INFO_ROUTE)

    companion object {
        const val HOME_ROUTE = "home"
        const val TASK_ROUTE = "task"
        const val INFO_ROUTE = "info"
    }
}
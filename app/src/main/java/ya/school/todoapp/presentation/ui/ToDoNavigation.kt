package ya.school.todoapp.presentation.ui

import androidx.navigation.NavHostController

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

    companion object {
        const val HOME_ROUTE = "home"
        const val TASK_ROUTE = "task"
    }
}
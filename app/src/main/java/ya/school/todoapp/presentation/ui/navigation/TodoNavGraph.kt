package ya.school.todoapp.presentation.ui.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.glide.GlideDivImageLoader
import ya.school.todoapp.presentation.ui.home.HomeScreen
import ya.school.todoapp.presentation.ui.info.InfoScreen
import ya.school.todoapp.presentation.ui.info.divkit.InfoDivActionHandler
import ya.school.todoapp.presentation.ui.settings.SettingsScreen
import ya.school.todoapp.presentation.ui.task.TaskFormScreen

@Composable
fun TodoNavGraph(activity: ComponentActivity, navigator: ToDoNavigation) {
    NavHost(
        navController = navigator.navController,
        startDestination = "home"
    ) {
        composable(ToDoNavigation.HOME_ROUTE) {
            HomeScreen(
                navigator = navigator,
                viewModel = hiltViewModel()
            )
        }
        composable(ToDoNavigation.TASK_ROUTE) {
            TaskFormScreen(
                navigator = navigator,
                viewModel = hiltViewModel(),
                taskId = null
            )
        }
        composable(
            "${ToDoNavigation.TASK_ROUTE}/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            TaskFormScreen(
                navigator = navigator,
                viewModel = hiltViewModel(),
                taskId = backStackEntry.arguments?.getString("taskId")
            )
        }
        composable(ToDoNavigation.INFO_ROUTE) {
            val divkitContext = Div2Context(
                baseContext = activity,
                configuration = createDivConfiguration(activity, navigator::navigateBack),
                lifecycleOwner = activity
            )

            InfoScreen(divContext = divkitContext)
        }
        composable(ToDoNavigation.SETTINGS_ROUTE) {
            SettingsScreen(
                navigator = navigator,
                viewModel = hiltViewModel()
            )
        }
    }
}

private fun createDivConfiguration(
    activity: ComponentActivity,
    navigateBack: () -> Unit
): DivConfiguration {
    return DivConfiguration.Builder(GlideDivImageLoader(activity))
        .actionHandler(InfoDivActionHandler(navigateBack))
        .visualErrorsEnabled(true)
        .build()
}
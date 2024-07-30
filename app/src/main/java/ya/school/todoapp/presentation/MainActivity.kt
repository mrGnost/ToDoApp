package ya.school.todoapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.glide.GlideDivImageLoader
import dagger.hilt.android.AndroidEntryPoint
import ya.school.todoapp.domain.entity.ThemeMode
import ya.school.todoapp.presentation.ui.navigation.ToDoNavigation
import ya.school.todoapp.presentation.ui.home.HomeScreen
import ya.school.todoapp.presentation.ui.info.InfoScreen
import ya.school.todoapp.presentation.ui.info.divkit.InfoDivActionHandler
import ya.school.todoapp.presentation.ui.navigation.TodoNavGraph
import ya.school.todoapp.presentation.ui.settings.SettingsScreen
import ya.school.todoapp.presentation.ui.task.TaskFormScreen
import ya.school.todoapp.presentation.ui.theme.ToDoAppTheme

/**
 * Главная активити приложения (и пока единственная)
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navigator: ToDoNavigation
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.startOnNetworkAvailableUpdates()

        setContent {
            val themeMode = viewModel.getThemeFlow().collectAsState()
            navigator = ToDoNavigation(rememberNavController())

            ToDoAppTheme(
                darkTheme = when (themeMode.value) {
                    ThemeMode.System -> isSystemInDarkTheme()
                    ThemeMode.Dark -> true
                    ThemeMode.Light -> false
                }
            ) {
                TodoNavGraph(this, navigator)
            }
        }
    }
}
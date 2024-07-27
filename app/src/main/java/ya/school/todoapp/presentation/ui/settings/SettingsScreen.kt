package ya.school.todoapp.presentation.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ya.school.todoapp.R
import ya.school.todoapp.presentation.ui.navigation.ToDoNavigation
import ya.school.todoapp.presentation.ui.components.MainSurface
import ya.school.todoapp.presentation.ui.components.ThemeRow
import ya.school.todoapp.presentation.ui.components.ToDoMainText
import ya.school.todoapp.presentation.ui.components.topbars.MainTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigator: ToDoNavigation,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val theme by viewModel.getThemeFlow().collectAsState()

    Scaffold(
        topBar = {
            MainTopBar(
                text = stringResource(id = R.string.settings),
            )
        }
    ) { innerPadding ->
        MainSurface(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ThemeRow(importance = theme) {
                    viewModel.setTheme(it)
                }
                HorizontalDivider()
                ToDoMainText(
                    text = "О приложении",
                    modifier = Modifier
                        .clickable { navigator.navigateToInfo() }
                )
            }
        }
    }
}
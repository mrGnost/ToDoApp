package ya.school.todoapp.presentation.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ya.school.todoapp.R
import ya.school.todoapp.presentation.ui.navigation.ToDoNavigation
import ya.school.todoapp.presentation.ui.components.ElevatedContainer
import ya.school.todoapp.presentation.ui.components.MainSurface
import ya.school.todoapp.presentation.ui.components.ToDoFAB
import ya.school.todoapp.presentation.ui.components.ToDoListColumn
import ya.school.todoapp.presentation.ui.components.topbars.MainTopBar
import ya.school.todoapp.presentation.ui.theme.AppTheme
import ya.school.todoapp.presentation.ui.theme.ToDoAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigator: ToDoNavigation,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(key1 = viewModel.snackBarMessage) {
        viewModel.snackBarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.snackBarMessage = null
        }
    }

    var showCheckedItems by remember {
        mutableStateOf(true)
    }

    val todoItems by viewModel.todoItemsFlow.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            MainTopBar(
                text = stringResource(id = R.string.my_tasks),
                subtext = "Выполнено - ${todoItems.count { it.isDone }}",
                scrollBehavior = scrollBehavior
            ) {
                VisibilityButton(
                    show = showCheckedItems
                ) {
                    showCheckedItems = !showCheckedItems
                }
                SettingsButton {
                    navigator.navigateToSettings()
                }
            }
        },
        floatingActionButton = {
            ToDoFAB(onClick = {
                navigator.navigateToTaskForm()
                viewModel.finish()
            }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Добавить задачу",
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        MainSurface(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            ElevatedContainer {
                val tasks = todoItems.filter { showCheckedItems || !it.isDone }.toMutableStateList()

                ToDoListColumn(
                    tasks = tasks,
                    onCompleteChange = { id, value ->
                        viewModel.changeItemCheck(id, value)
                    },
                    onPickItem = { id ->
                        navigator.navigateToTask(id)
                        viewModel.finish()
                    },
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun VisibilityButton(
    modifier: Modifier = Modifier,
    show: Boolean = true,
    onClick: () -> Unit
) {
    val description = stringResource(id = R.string.show_done_description)

    IconButton(
        modifier = modifier
            .semantics {
                contentDescription = description
                selected = show
                testTag = "complete_visible_button"
            },
        onClick = onClick
    ) {
        val visibilityIcon = when (show) {
            true -> R.drawable.visible
            false -> R.drawable.invisible
        }

        Icon(
            painter = painterResource(id = visibilityIcon),
            contentDescription = null,
            tint = AppTheme.colors.blue
        )
    }
}

@Composable
fun SettingsButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val description = stringResource(id = R.string.open_settings_description)

    IconButton(
        modifier = modifier
            .semantics {
                contentDescription = description
            },
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.settings),
            contentDescription = null,
            tint = AppTheme.colors.blue
        )
    }
}

@Preview
@Composable
fun HomeScreenPreviewLight() {
    ToDoAppTheme(darkTheme = false) {
        HomeScreen(navigator = ToDoNavigation(NavHostController(LocalContext.current)), hiltViewModel())
    }
}

@Preview
@Composable
fun HomeScreenPreviewDark() {
    ToDoAppTheme(darkTheme = true) {
        HomeScreen(navigator = ToDoNavigation(NavHostController(LocalContext.current)), hiltViewModel())
    }
}
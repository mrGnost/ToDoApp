package ya.school.todoapp.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ya.school.todoapp.R
import ya.school.todoapp.presentation.ui.ToDoNavigation
import ya.school.todoapp.presentation.ui.components.ElevatedContainer
import ya.school.todoapp.presentation.ui.components.MainSurface
import ya.school.todoapp.presentation.ui.components.ToDoFAB
import ya.school.todoapp.presentation.ui.components.ToDoListColumn
import ya.school.todoapp.presentation.ui.components.ToDoSubText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigator: ToDoNavigation) {
    val viewModel: HomeViewModel = hiltViewModel()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var showCheckedItems by remember {
        mutableStateOf(true)
    }

    val todoItems by viewModel.todoItemsFlow.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(stringResource(id = R.string.my_tasks))
                        ToDoSubText(text = "Выполнено - ${todoItems.count { it.isDone }}")
                    }
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = {
                        showCheckedItems = !showCheckedItems
                    }) {
                        val visibilityIcon = when (showCheckedItems) {
                            true -> R.drawable.visible
                            false -> R.drawable.invisible
                        }

                        Icon(
                            painter = painterResource(id = visibilityIcon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ToDoFAB(onClick = {
                navigator.navigateToTaskForm()
            }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Добавить задачу",
                )
            }
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
                    onDelete = { id ->
                        viewModel.removeItem(id)
                    },
                    onPickItem = { id ->
                        navigator.navigateToTask(id)
                    },
                    modifier = Modifier
                )
            }
        }
    }
}
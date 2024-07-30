package ya.school.todoapp.presentation.ui.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ya.school.todoapp.R
import ya.school.todoapp.presentation.ui.navigation.ToDoNavigation
import ya.school.todoapp.presentation.ui.components.DeadlineRow
import ya.school.todoapp.presentation.ui.components.FormTextInput
import ya.school.todoapp.presentation.ui.components.ImportanceRow
import ya.school.todoapp.presentation.ui.components.MainSurface
import ya.school.todoapp.presentation.ui.components.RemoveButton
import ya.school.todoapp.presentation.ui.components.topbars.FormTopBar
import ya.school.todoapp.presentation.ui.theme.ToDoAppTheme
import ya.school.todoapp.presentation.ui.util.DateUtil.toDateString

@Composable
fun TaskFormScreen(
    navigator: ToDoNavigation,
    viewModel: TaskFormViewModel = hiltViewModel(),
    taskId: String? = null
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val composableScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        taskId?.let {
            viewModel.getItem(it)
        }
    }

    LaunchedEffect(key1 = viewModel.snackBarMessage) {
        viewModel.snackBarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.snackBarMessage = null
        }
    }

    Scaffold(
        topBar = {
            FormTopBar(
                onClose = {
                    navigator.navigateToHome()
                },
                onSave = {
                    composableScope.launch {
                        if (viewModel.saveItem(taskId)) {
                            viewModel.finish()
                            navigator.navigateToHome()
                        }
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {  innerPadding ->
        MainSurface(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                FormTextInput(
                    text = viewModel.currentText,
                    onTextChange = { viewModel.currentText = it },
                    hint = stringResource(id = R.string.text_hint),
                    modifier = Modifier
                        .heightIn(200.dp, Dp.Infinity)
                        .fillMaxWidth()
                )

                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ImportanceRow(
                        importance = viewModel.currentImportance,
                        onPick = { viewModel.currentImportance = it }
                    )
                    HorizontalDivider()
                    DeadlineRow(
                        date = viewModel.currentDate?.toDateString(),
                        onDateChanged = { viewModel.currentDate = it }
                    )
                    HorizontalDivider()
                    RemoveButton(
                        isActive = taskId != null,
                        onClick = {
                            composableScope.launch {
                                if (viewModel.removeItem(taskId!!)) {
                                    viewModel.finish()
                                    navigator.navigateToHome()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskFormScreenPreviewLight() {
    ToDoAppTheme(darkTheme = false) {
        TaskFormScreen(
            navigator = ToDoNavigation(NavHostController(LocalContext.current)),
            viewModel = hiltViewModel(),
            taskId = "0"
        )
    }
}

@Preview
@Composable
fun TaskFormScreenPreviewDark() {
    ToDoAppTheme(darkTheme = true) {
        TaskFormScreen(
            navigator = ToDoNavigation(NavHostController(LocalContext.current)),
            viewModel = hiltViewModel(),
            taskId = "0"
        )
    }
}
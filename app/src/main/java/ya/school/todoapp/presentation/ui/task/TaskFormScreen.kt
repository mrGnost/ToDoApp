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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ya.school.todoapp.R
import ya.school.todoapp.presentation.ui.ToDoNavigation
import ya.school.todoapp.presentation.ui.components.DeadlineRow
import ya.school.todoapp.presentation.ui.components.FormTextInput
import ya.school.todoapp.presentation.ui.components.topbars.FormTopBar
import ya.school.todoapp.presentation.ui.components.ImportanceRow
import ya.school.todoapp.presentation.ui.components.MainSurface
import ya.school.todoapp.presentation.ui.components.RemoveButton
import ya.school.todoapp.presentation.ui.util.DateUtil.toDateString

@Composable
fun TaskFormScreen(navigator: ToDoNavigation, taskId: String?) {
    val viewModel: TaskFormViewModel = hiltViewModel()

    LaunchedEffect(key1 = Unit) {
        taskId?.let {
            viewModel.getItem(it)
        }
    }

    Scaffold(
        topBar = {
            FormTopBar(
                onClose = {
                    navigator.navigateToHome()
                },
                onSave = {
                    viewModel.saveItem(taskId)
                    navigator.navigateToHome()
                }
            )
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
                            viewModel.removeItem(taskId!!)
                            navigator.navigateToHome()
                        }
                    )
                }
            }
        }
    }
}
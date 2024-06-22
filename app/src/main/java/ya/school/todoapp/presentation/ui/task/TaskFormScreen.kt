package ya.school.todoapp.presentation.ui.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ya.school.todoapp.presentation.ui.ToDoNavigation
import ya.school.todoapp.presentation.ui.components.DeadlineRow
import ya.school.todoapp.presentation.ui.components.FormTextInput
import ya.school.todoapp.presentation.ui.components.FormTopBar
import ya.school.todoapp.presentation.ui.components.ImportancePicker
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
                    navigator.navigateBack()
                },
                onSave = {
                    viewModel.saveItem(taskId)
                    navigator.navigateBack()
                }
            )
        }
    ) {  innerPadding ->
        MainSurface(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                FormTextInput(
                    text = viewModel.currentText,
                    onTextChange = { viewModel.currentText = it },
                    hint = "Что надо сделать...",
                    modifier = Modifier
                        .heightIn(200.dp, Dp.Infinity)
                        .fillMaxWidth()
                )

                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize()
                ) {
                    var importancePickerExpanded by remember {
                        mutableStateOf(false)
                    }

                    Column(
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ImportanceRow(
                            importance = viewModel.currentImportance,
                            onClick = { importancePickerExpanded = true }
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
                                navigator.navigateBack()
                            }
                        )
                    }
                    ImportancePicker(
                        expanded = importancePickerExpanded,
                        onDismiss = { importancePickerExpanded = false },
                        onPick = {
                            viewModel.currentImportance = it
                            importancePickerExpanded = false
                        },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                    )
                }
            }
        }
    }
}
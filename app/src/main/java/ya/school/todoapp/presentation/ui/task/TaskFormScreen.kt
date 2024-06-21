package ya.school.todoapp.presentation.ui.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ya.school.todoapp.presentation.ui.ToDoNavigation
import ya.school.todoapp.presentation.ui.components.FormTextInput
import ya.school.todoapp.presentation.ui.components.FormTopBar
import ya.school.todoapp.presentation.ui.components.ImportancePicker
import ya.school.todoapp.presentation.ui.components.MainSurface

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
            Column {
                FormTextInput(
                    text = viewModel.currentText,
                    onTextChange = { viewModel.currentText = it },
                    hint = "Что надо сделать...",
                    modifier = Modifier
                        .heightIn(200.dp, Dp.Infinity)
                        .fillMaxWidth()
                )

                Box {
                    var importancePickerExpanded by remember {
                        mutableStateOf(false)
                    }

                    Column {

                    }
                    ImportancePicker(
                        expanded = importancePickerExpanded,
                        onDismiss = { importancePickerExpanded = false },
                        onPick = { viewModel.currentImportance = it }
                    )
                }
            }
        }
    }
}
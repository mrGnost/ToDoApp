package ya.school.todoapp.presentation.ui.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ya.school.todoapp.presentation.ui.ToDoNavigation
import ya.school.todoapp.presentation.ui.components.ElevatedContainer
import ya.school.todoapp.presentation.ui.components.FormTopBar
import ya.school.todoapp.presentation.ui.components.MainSurface

@Composable
fun TaskFormScreen(navigator: ToDoNavigation, taskId: String?) {
    val viewModel: TaskFormViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            FormTopBar(
                onClose = {
                    navigator.navigateBack()
                },
                onSave = {
                    navigator.navigateBack()
                }
            )
        }
    ) {  innerPadding ->
        MainSurface(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            ElevatedContainer {

            }
        }
    }
}
package ya.school.todoapp.presentation.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ya.school.todoapp.data.TodoItem
import ya.school.todoapp.domain.repository.TodoItemsRepository
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    val repository: TodoItemsRepository
) : ViewModel() {
    var currentText by mutableStateOf("")
    var currentImportance by mutableStateOf(TodoItem.Importance.Regular)
    var currentDate: Date? by mutableStateOf(null)

    fun getItem(id: String) {
        viewModelScope.launch {
            with(repository.getItem(id)) {
                currentText = text
                currentImportance = importance
                currentDate = deadline
            }
        }
    }

    fun saveItem(id: String?) {
        viewModelScope.launch {
            if (id == null) {
                repository.addItem(
                    text = currentText,
                    importance = currentImportance,
                    deadline = currentDate
                )
            } else {
                repository.changeItem(
                    id = id,
                    text = currentText,
                    importance = currentImportance,
                    deadline = currentDate
                )
            }
        }
    }

}
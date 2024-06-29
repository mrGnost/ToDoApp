package ya.school.todoapp.presentation.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ya.school.todoapp.data.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
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
    var snackBarMessage: String? by mutableStateOf(null)

    fun getItem(id: String) {
        viewModelScope.launch {
            when (val result = repository.getItem(id)) {
                is TodoResult.Success -> with(result.data) {
                    currentText = text
                    currentImportance = importance
                    currentDate = deadline
                }
                is TodoResult.Error<*> -> {
                    processError(result.message)
                }
            }
        }
    }

    fun saveItem(id: String?) {
        viewModelScope.launch {
            val result = if (id == null) {
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
            if (result is TodoResult.Error<*>) {
                processError(result.message)
            }
        }
    }

    fun removeItem(id: String) {
        viewModelScope.launch {
            val result = repository.removeItem(id)
            if (result is TodoResult.Error<*>) {
                processError(result.message)
            }
        }
    }

    private fun processError(message: String) {
        snackBarMessage = message
    }
}
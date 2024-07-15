package ya.school.todoapp.presentation.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.usecase.AddItemUseCase
import ya.school.todoapp.domain.usecase.EditItemUseCase
import ya.school.todoapp.domain.usecase.GetItemUseCase
import ya.school.todoapp.domain.usecase.RemoveItemUseCase
import java.util.Date
import javax.inject.Inject

/**
 * Вьюмодель экрана с формой редактирования задачи
 */
@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val addItemUseCase: AddItemUseCase,
    private val removeItemUseCase: RemoveItemUseCase,
    private val editItemUseCase: EditItemUseCase,
    private val getItemUseCase: GetItemUseCase
) : ViewModel() {
    lateinit var todoItem: TodoItem
    var currentText by mutableStateOf("")
    var currentImportance by mutableStateOf(TodoItem.Importance.Regular)
    var currentDate: Date? by mutableStateOf(null)
    var snackBarMessage: String? by mutableStateOf(null)

    fun getItem(id: String) {
        viewModelScope.launch {
            when (val result = getItemUseCase(id)) {
                is TodoResult.Success -> with(result.data) {
                    todoItem = this
                    currentText = text
                    currentImportance = importance
                    currentDate = deadline
                }
                is TodoResult.Error -> {
                    processError(result.message)
                }
            }
        }
    }

    suspend fun saveItem(id: String?): Boolean {
        val result = viewModelScope.async {
            if (id == null) {
                addItemUseCase(
                    text = currentText,
                    importance = currentImportance,
                    deadline = currentDate
                )
            } else {
                todoItem = todoItem.copy(
                    text = currentText,
                    importance = currentImportance,
                    deadline = currentDate
                )
                editItemUseCase(todoItem)
            }
        }.await() // Требование: отменять всю background work при уходе с экрана - но нам нужно доделать сохранение
        return processEmptyResult(result)
    }

    suspend fun removeItem(id: String): Boolean {
        val result = viewModelScope.async {
            removeItemUseCase(id)
        }.await()
        return processEmptyResult(result)
    }

    private fun processEmptyResult(result: TodoResult<TodoItem>): Boolean {
        return when (result) {
            is TodoResult.Success -> {
                true
            }
            is TodoResult.Error -> {
                processError(result.message)
                false
            }
        }
    }

    fun finish() {
        viewModelScope.cancel()
    }

    private fun processError(message: String) {
        snackBarMessage = message
    }
}
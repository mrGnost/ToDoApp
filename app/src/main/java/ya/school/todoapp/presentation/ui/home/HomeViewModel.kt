package ya.school.todoapp.presentation.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.repository.TodoItemsRepository
import ya.school.todoapp.domain.usecase.ChangeCompletionUseCase
import ya.school.todoapp.domain.usecase.GetAllItemsUseCase
import javax.inject.Inject

/**
 * Вьюмодель главного экрана со списком задач
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: TodoItemsRepository,
    val getAllItemsUseCase: GetAllItemsUseCase,
    val changeCompletionUseCase: ChangeCompletionUseCase
) : ViewModel() {
    private val _todoItemsFlow = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoItemsFlow: Flow<List<TodoItem>>
        get() = _todoItemsFlow

    var snackBarMessage: String? by mutableStateOf(null)

    init {
        viewModelScope.launch {
            startItemsObservation()
        }
    }

    private suspend fun startItemsObservation() {
        when (val result = getAllItemsUseCase()) {
            is TodoResult.Success -> result.data.collect {
                _todoItemsFlow.value = it
            }
            is TodoResult.Error -> {
                processError(result.message)
            }
        }
    }

    fun changeItemCheck(id: String, newValue: Boolean) {
        viewModelScope.launch {
            val result = changeCompletionUseCase(id, newValue)
            if (result is TodoResult.Error) {
                processError(result.message)
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
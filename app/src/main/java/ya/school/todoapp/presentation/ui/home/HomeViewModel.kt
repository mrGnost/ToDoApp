package ya.school.todoapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ya.school.todoapp.data.TodoItem
import ya.school.todoapp.domain.repository.TodoItemsRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: TodoItemsRepository
) : ViewModel() {
    val todoItemsFlow: Flow<List<TodoItem>>
        get() = repository.getItems()

    fun removeItem(id: String) {
        viewModelScope.launch {
            repository.removeItem(id)
        }
    }

    fun changeItemCheck(id: String, newValue: Boolean) {
        viewModelScope.launch {
            repository.changeCompletionStatus(id, newValue)
        }
    }
}
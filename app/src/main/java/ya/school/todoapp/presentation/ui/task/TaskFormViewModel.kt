package ya.school.todoapp.presentation.ui.task

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ya.school.todoapp.data.TodoItem
import ya.school.todoapp.domain.repository.TodoItemsRepository
import javax.inject.Inject

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    val repository: TodoItemsRepository
) : ViewModel() {

    fun getItem(id: String) {
        viewModelScope.launch {
            repository.getItem(id)
        }
    }

}
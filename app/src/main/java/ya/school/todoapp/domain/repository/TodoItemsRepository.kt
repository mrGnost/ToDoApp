package ya.school.todoapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ya.school.todoapp.data.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import java.util.Date

// Замоканные данные, так что ошибок нет - изменю, когда будем подключать сеть
interface TodoItemsRepository {
    fun getItems(): TodoResult<Flow<List<TodoItem>>>

    suspend fun addItem(
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ): TodoResult<Unit>

    suspend fun changeCompletionStatus(id: String, complete: Boolean): TodoResult<Unit>

    suspend fun removeItem(id: String): TodoResult<Unit>

    suspend fun changeItem(
        id: String,
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ): TodoResult<Unit>

    suspend fun getItem(id: String): TodoResult<TodoItem>
}
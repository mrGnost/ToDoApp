package ya.school.todoapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ya.school.todoapp.data.TodoItem
import java.util.Date

interface TodoItemsRepository {
    fun getItems(): Flow<List<TodoItem>>

    suspend fun addItem(
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    )

    suspend fun changeCompletionStatus(id: String, complete: Boolean)

    suspend fun removeItem(id: String)

    suspend fun changeItem(
        id: String,
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    )

    suspend fun getItem(id: String): TodoItem
}
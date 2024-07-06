package ya.school.todoapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import java.util.Date

/**
 * Интерфейс репозитория, отвечающего за взаимодействие с локальным хранилищем задач
 */
interface TodoItemsRepository {
    fun getItems(): TodoResult<Flow<List<TodoItem>>>

    suspend fun updateAllItems(items: List<TodoItem>): TodoResult<Unit>

    suspend fun addItem(
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ): TodoResult<TodoItem>

    suspend fun changeCompletionStatus(id: String, complete: Boolean): TodoResult<TodoItem>

    suspend fun removeItem(id: String): TodoResult<TodoItem>

    suspend fun changeItem(
        id: String,
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ): TodoResult<TodoItem>

    suspend fun getItem(id: String): TodoResult<TodoItem>
}
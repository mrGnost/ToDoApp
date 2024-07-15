package ya.school.todoapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import java.util.Date

/**
 * Интерфейс репозитория, отвечающего за взаимодействие с локальным хранилищем задач
 */
interface TodoItemsRepository {
    fun getItems(): Flow<List<TodoItem>>

    suspend fun updateAllItems(items: List<TodoItem>)

    suspend fun addItem(
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ): String

    suspend fun changeCompletionStatus(id: String, complete: Boolean)

    suspend fun removeItem(id: String)

    suspend fun changeItem(item: TodoItem)

    suspend fun getItem(id: String): TodoResult<TodoItem>
}
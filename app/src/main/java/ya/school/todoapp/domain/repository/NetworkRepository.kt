package ya.school.todoapp.domain.repository

import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoItemList
import ya.school.todoapp.domain.entity.TodoResult

/**
 * Интерфейс репозитория, работающего с API бэкенда
 */
interface NetworkRepository {
    suspend fun getAllItems(): TodoResult<TodoItemList>

    suspend fun updateItems(items: TodoItemList, revision: Int): TodoResult<Unit>

    suspend fun getItem(id: String): TodoResult<TodoItem>

    suspend fun addItem(item: TodoItem): TodoResult<Unit>

    suspend fun removeItem(id: String): TodoResult<Unit>

    suspend fun changeItem(item: TodoItem): TodoResult<Unit>
}
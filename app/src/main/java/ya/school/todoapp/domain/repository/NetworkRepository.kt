package ya.school.todoapp.domain.repository

import ya.school.todoapp.domain.entity.Revisioned
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult

/**
 * Интерфейс репозитория, работающего с API бэкенда
 */
interface NetworkRepository {
    suspend fun getAllItems(): TodoResult<Revisioned<List<TodoItem>>>

    suspend fun updateItems(
        items: Revisioned<List<TodoItem>>
    ): TodoResult<Revisioned<List<TodoItem>>>

    suspend fun getItem(id: String): TodoResult<Revisioned<TodoItem>>

    suspend fun addItem(item: Revisioned<TodoItem>): TodoResult<Revisioned<TodoItem>>

    suspend fun removeItem(id: String, revision: Int): TodoResult<Revisioned<TodoItem>>

    suspend fun changeItem(item: Revisioned<TodoItem>): TodoResult<Revisioned<TodoItem>>
}
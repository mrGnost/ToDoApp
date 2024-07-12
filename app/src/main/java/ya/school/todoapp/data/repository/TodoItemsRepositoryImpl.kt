package ya.school.todoapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import ya.school.todoapp.data.storage.db.TodoDao
import ya.school.todoapp.data.storage.db.entity.TodoItemEntity
import ya.school.todoapp.data.storage.db.entity.toTodoItemEntities
import ya.school.todoapp.data.storage.db.entity.toTodoItemEntity
import ya.school.todoapp.data.storage.db.entity.toTodoItems
import ya.school.todoapp.data.storage.db.entity.toTodoItemsFlow
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.repository.TodoItemsRepository
import java.util.Date
import java.util.UUID
import javax.inject.Inject

/**
 * Имплементация репозитория, отвечающего за взаимодействие с локальным хранилищем задач
 */
class TodoItemsRepositoryImpl @Inject constructor(
    private val dao: TodoDao
) : TodoItemsRepository {
    override fun getItems(): Flow<List<TodoItem>> {
        return dao.getTodoItems().toTodoItemsFlow()
    }

    override suspend fun updateAllItems(items: List<TodoItem>) = withContext(Dispatchers.IO) {
        val newItems = items.toTodoItemEntities().toSet()
        val currentItems = dao.getTodoItems().first().toSet()
        val toRemove = currentItems.minus(newItems)
        val toInsert = newItems.minus(currentItems)
        dao.deleteTodoItems(toRemove)
        dao.insertTodoItems(toInsert)
    }

    override suspend fun addItem(
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ): String = withContext(Dispatchers.IO) {
        val timestamp = Date()
        val id = UUID.randomUUID().toString()
        val item = TodoItemEntity(
            id = id,
            text = text,
            importance = importance,
            deadline = deadline,
            done = false,
            createdAt = timestamp,
            changedAt = timestamp,
            lastUpdatedBy = "1"
        )
        dao.insertTodoItem(item)
        return@withContext id
    }

    override suspend fun changeCompletionStatus(
        id: String,
        complete: Boolean
    ) = withContext(Dispatchers.IO) {
        dao.updateDoneStatus(id, complete)
    }

    override suspend fun removeItem(id: String) = withContext(Dispatchers.IO) {
        dao.deleteTodoItem(id)
    }

    override suspend fun changeItem(item: TodoItem) = withContext(Dispatchers.IO) {
        dao.updateTodoItem(item.copy(changedAt = Date()).toTodoItemEntity())
    }

    override suspend fun getItem(id: String): TodoResult<TodoItem> = withContext(Dispatchers.IO) {
        val items = dao.getTodoItem(id).toTodoItems()
        if (items.isEmpty())
            return@withContext TodoResult.Error("Задача не найдена")
        return@withContext TodoResult.Success(items.first())
    }
}
package ya.school.todoapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.data.TodoItemsSource
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.repository.TodoItemsRepository
import java.util.Date
import javax.inject.Inject

/**
 * Имплементация репозитория, отвечающего за взаимодействие с локальным хранилищем задач
 */
class TodoItemsRepositoryImpl @Inject constructor(
    private val dataSource: TodoItemsSource
) : TodoItemsRepository {
    override fun getItems(): TodoResult<Flow<List<TodoItem>>> {
        return dataSource.itemsFlow
    }

    override suspend fun updateAllItems(items: List<TodoItem>): TodoResult<Unit> =
        withContext(Dispatchers.IO) {
            dataSource.updateItems(items)
        }

    override suspend fun addItem(
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ): TodoResult<Unit> = withContext(Dispatchers.IO) {
        dataSource.addItem(text, importance, deadline)
    }

    override suspend fun changeCompletionStatus(
        id: String,
        complete: Boolean
    ): TodoResult<Unit> = withContext(Dispatchers.IO) {
        dataSource.changeCheckedStatus(id, complete)
    }

    override suspend fun removeItem(id: String): TodoResult<Unit> = withContext(Dispatchers.IO) {
        dataSource.removeItem(id)
    }

    override suspend fun changeItem(
        id: String,
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ): TodoResult<Unit> = withContext(Dispatchers.IO) {
        dataSource.changeItem(id, text, importance, deadline)
    }

    override suspend fun getItem(id: String): TodoResult<TodoItem> = withContext(Dispatchers.IO) {
        dataSource.getItem(id)
    }
}
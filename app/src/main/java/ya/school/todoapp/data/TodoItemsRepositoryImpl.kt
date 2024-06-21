package ya.school.todoapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import ya.school.todoapp.domain.repository.TodoItemsRepository
import java.util.Date
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    private val dataSource: TodoItemsSource
) : TodoItemsRepository {
    override fun getItems(): Flow<List<TodoItem>> {
        return dataSource.itemsFlow
    }

    override suspend fun addItem(
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ) = withContext(Dispatchers.IO) {
        dataSource.addItem(text, importance, deadline)
    }

    override suspend fun changeCompletionStatus(
        id: String,
        complete: Boolean
    ) = withContext(Dispatchers.IO) {
        dataSource.changeCheckedStatus(id, complete)
    }

    override suspend fun removeItem(id: String) = withContext(Dispatchers.IO) {
        dataSource.removeItem(id)
    }

    override suspend fun changeItem(
        id: String,
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ) = withContext(Dispatchers.IO) {
        dataSource.changeItem(id, text, importance, deadline)
    }

    override suspend fun getItem(id: String): TodoItem = withContext(Dispatchers.IO) {
        dataSource.getItem(id)
    }
}
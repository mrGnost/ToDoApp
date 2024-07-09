package ya.school.todoapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ya.school.todoapp.data.network.NetworkUtils
import ya.school.todoapp.data.network.TodoApi
import ya.school.todoapp.data.network.entity.TodoItemRequestDTO
import ya.school.todoapp.data.network.entity.TodoItemResponseDTO
import ya.school.todoapp.data.network.entity.TodoListRequestDTO
import ya.school.todoapp.data.network.entity.TodoListResponseDTO
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoItemList
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.repository.NetworkRepository
import javax.inject.Inject

/**
 * Имплементация репозитория, работающего с API бэкенда
 */
class NetworkRepositoryImpl @Inject constructor(
    private val api: TodoApi
) : NetworkRepository {
    override suspend fun getAllItems(): TodoResult<TodoItemList> = withContext(Dispatchers.IO) {
        NetworkUtils.getResponse(
            TodoListResponseDTO::toTodoItems
        ) {
            api.getAllItems()
        }
    }

    override suspend fun updateItems(
        items: TodoItemList,
        revision: Int
    ): TodoResult<TodoItem> = withContext(Dispatchers.IO) {
        NetworkUtils.getResponse(
            TodoListResponseDTO::toTodoItems
        ) {
            api.updateList(revision, TodoListRequestDTO.fromTodoItems(items))
        }
    }

    override suspend fun getItem(id: String): TodoResult<TodoItem> = withContext(Dispatchers.IO) {
        NetworkUtils.getResponse(
            TodoItemResponseDTO::toTodoItem
        ) {
            api.getItem(id)
        }
    }

    override suspend fun addItem(item: TodoItem): TodoResult<TodoItem> = withContext(Dispatchers.IO) {
        NetworkUtils.getResponse(
            TodoItemResponseDTO::toTodoItem
        ) {
            val r = api.addItem(item.revision, TodoItemRequestDTO.fromTodoItem(item))
            r
        }
    }

    override suspend fun removeItem(id: String, revision: Int): TodoResult<TodoItem> = withContext(Dispatchers.IO) {
        NetworkUtils.getResponse(
            TodoItemResponseDTO::toTodoItem
        ) {
            api.deleteItem(revision, id)
        }
    }

    override suspend fun changeItem(item: TodoItem): TodoResult<TodoItem> = withContext(Dispatchers.IO) {
        NetworkUtils.getResponse(
            TodoItemResponseDTO::toTodoItem
        ) {
            api.changeItem(item.revision, item.id, TodoItemRequestDTO.fromTodoItem(item))
        }
    }
}
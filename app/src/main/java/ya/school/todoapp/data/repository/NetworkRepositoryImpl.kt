package ya.school.todoapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ya.school.todoapp.data.network.NetworkUtils
import ya.school.todoapp.data.network.TodoApi
import ya.school.todoapp.data.network.entity.TodoItemRequestDTO
import ya.school.todoapp.data.network.entity.TodoItemResponseDTO
import ya.school.todoapp.data.network.entity.TodoListRequestDTO
import ya.school.todoapp.data.network.entity.TodoListResponseDTO
import ya.school.todoapp.domain.entity.Revisioned
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.repository.NetworkRepository
import javax.inject.Inject

/**
 * Имплементация репозитория, работающего с API бэкенда
 */
class NetworkRepositoryImpl @Inject constructor(
    private val api: TodoApi
) : NetworkRepository {
    override suspend fun getAllItems(): TodoResult<Revisioned<List<TodoItem>>> =
        withContext(Dispatchers.IO) {
            NetworkUtils.getResponse(
                TodoListResponseDTO::toTodoItems
            ) {
                api.getAllItems()
            }
        }

    override suspend fun updateItems(
        items: Revisioned<List<TodoItem>>
    ): TodoResult<Revisioned<List<TodoItem>>> = withContext(Dispatchers.IO) {
        NetworkUtils.getResponse(
            TodoListResponseDTO::toTodoItems
        ) {
            api.updateList(items.revision, TodoListRequestDTO.fromTodoItems(items.data))
        }
    }

    override suspend fun getItem(id: String): TodoResult<Revisioned<TodoItem>> =
        withContext(Dispatchers.IO) {
            NetworkUtils.getResponse(
                TodoItemResponseDTO::toTodoItem
            ) {
                api.getItem(id)
            }
        }

    override suspend fun addItem(item: Revisioned<TodoItem>): TodoResult<Revisioned<TodoItem>> =
        withContext(Dispatchers.IO) {
            NetworkUtils.getResponse(
                TodoItemResponseDTO::toTodoItem
            ) {
                api.addItem(item.revision, TodoItemRequestDTO.fromTodoItem(item.data))
            }
        }

    override suspend fun removeItem(id: String, revision: Int): TodoResult<Revisioned<TodoItem>> =
        withContext(Dispatchers.IO) {
            NetworkUtils.getResponse(
                TodoItemResponseDTO::toTodoItem
            ) {
                api.deleteItem(revision, id)
            }
        }

    override suspend fun changeItem(item: Revisioned<TodoItem>): TodoResult<Revisioned<TodoItem>> =
        withContext(Dispatchers.IO) {
            NetworkUtils.getResponse(
                TodoItemResponseDTO::toTodoItem
            ) {
                api.changeItem(item.revision, item.data.id, TodoItemRequestDTO.fromTodoItem(item.data))
            }
        }
}
package ya.school.todoapp.domain.usecase

import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.repository.DatastoreRepository
import ya.school.todoapp.domain.repository.NetworkRepository
import ya.school.todoapp.domain.repository.TodoItemsRepository
import javax.inject.Inject

/**
 * Последовательность действий при попытке удалить элемент
 */
class RemoveItemUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val datastoreRepository: DatastoreRepository,
    private val todoItemsRepository: TodoItemsRepository
) {
    suspend operator fun invoke(id: String): TodoResult<TodoItem> {
        when (val result = todoItemsRepository.removeItem(id)) {
            is TodoResult.Error -> return TodoResult.Error(result.message)
            is TodoResult.Success -> {
                val revision = datastoreRepository.getRevision()
                val backendResponse = networkRepository.removeItem(id, revision)
                if (backendResponse is TodoResult.Success)
                    datastoreRepository.setRevision(result.data.revision)
                return backendResponse
            }
        }
    }
}
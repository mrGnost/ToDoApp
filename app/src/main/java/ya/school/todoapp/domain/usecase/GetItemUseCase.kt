package ya.school.todoapp.domain.usecase

import kotlinx.coroutines.flow.first
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.entity.toResult
import ya.school.todoapp.domain.repository.DatastoreRepository
import ya.school.todoapp.domain.repository.NetworkRepository
import ya.school.todoapp.domain.repository.SystemRepository
import ya.school.todoapp.domain.repository.TodoItemsRepository
import javax.inject.Inject

/**
 * Последовательность действий при попытке получить элемент по ID
 */
class GetItemUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val datastoreRepository: DatastoreRepository,
    private val todoItemsRepository: TodoItemsRepository,
    private val systemRepository: SystemRepository
) {
    suspend operator fun invoke(id: String): TodoResult<TodoItem> {
        if (systemRepository.getNetworkState()) {
            val result = networkRepository.getItem(id)
            if (result is TodoResult.Success) {
                datastoreRepository.setRevision(result.data.revision)
                todoItemsRepository.changeItem(result.data.data)
            }
            return result.toResult()
        }
        return todoItemsRepository.getItem(id)
    }
}
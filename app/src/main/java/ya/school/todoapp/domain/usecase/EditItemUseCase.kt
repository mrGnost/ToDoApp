package ya.school.todoapp.domain.usecase

import kotlinx.coroutines.flow.first
import ya.school.todoapp.domain.entity.Revisioned
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.entity.toResult
import ya.school.todoapp.domain.repository.DatastoreRepository
import ya.school.todoapp.domain.repository.NetworkRepository
import ya.school.todoapp.domain.repository.SystemRepository
import ya.school.todoapp.domain.repository.TodoItemsRepository
import javax.inject.Inject

/**
 * Последовательность действий при попытке изменить элемент
 */
class EditItemUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val datastoreRepository: DatastoreRepository,
    private val todoItemsRepository: TodoItemsRepository,
    private val systemRepository: SystemRepository
) {
    suspend operator fun invoke(item: TodoItem): TodoResult<TodoItem> {
        todoItemsRepository.changeItem(item)
        if (systemRepository.getNetworkState()) {
            val revision = datastoreRepository.getRevision()
            val itemToSend = Revisioned(
                data = item,
                revision = revision
            )
            val backendResponse = networkRepository.changeItem(itemToSend)
            if (backendResponse is TodoResult.Success)
                datastoreRepository.setRevision(backendResponse.data.revision)
            return backendResponse.toResult()
        }
        return TodoResult.Success(item)
    }
}
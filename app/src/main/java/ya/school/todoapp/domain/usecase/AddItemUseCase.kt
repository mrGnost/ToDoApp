package ya.school.todoapp.domain.usecase

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import ya.school.todoapp.domain.entity.Revisioned
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.entity.toResult
import ya.school.todoapp.domain.repository.DatastoreRepository
import ya.school.todoapp.domain.repository.NetworkRepository
import ya.school.todoapp.domain.repository.SystemRepository
import ya.school.todoapp.domain.repository.TodoItemsRepository
import java.util.Date
import javax.inject.Inject

/**
 * Последовательность действий при попытке добавить элемент
 */
class AddItemUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val datastoreRepository: DatastoreRepository,
    private val todoItemsRepository: TodoItemsRepository,
    private val systemRepository: SystemRepository
) {
    suspend operator fun invoke(
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ): TodoResult<TodoItem> {
        val id = todoItemsRepository.addItem(text, importance, deadline)
        val result = todoItemsRepository.getItem(id)
        if (systemRepository.getNetworkState()) {
            val revision = datastoreRepository.getRevision()
            if (result is TodoResult.Success) {
                val itemToSend = Revisioned(
                    data = result.data,
                    revision = revision
                )
                val backendResponse = networkRepository.addItem(itemToSend)
                if (backendResponse is TodoResult.Success)
                    datastoreRepository.setRevision(backendResponse.data.revision)
                return backendResponse.toResult()
            }
        }
        return result
    }
}
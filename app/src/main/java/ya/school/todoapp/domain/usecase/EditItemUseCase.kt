package ya.school.todoapp.domain.usecase

import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.repository.DatastoreRepository
import ya.school.todoapp.domain.repository.NetworkRepository
import ya.school.todoapp.domain.repository.TodoItemsRepository
import java.util.Date
import javax.inject.Inject

/**
 * Последовательность действий при попытке изменить элемент
 */
class EditItemUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val datastoreRepository: DatastoreRepository,
    private val todoItemsRepository: TodoItemsRepository
) {
    suspend operator fun invoke(
        id: String,
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ): TodoResult<TodoItem> {
        when (val result = todoItemsRepository.changeItem(id, text, importance, deadline)) {
            is TodoResult.Error -> return TodoResult.Error(result.message)
            is TodoResult.Success -> {
                val revision = datastoreRepository.getRevision()
                val item = result.data.copy(revision = revision)
                val backendResponse = networkRepository.changeItem(item)
                if (backendResponse is TodoResult.Success)
                    datastoreRepository.setRevision(result.data.revision)
                return backendResponse
            }
        }
    }
}
package ya.school.todoapp.domain.usecase

import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.repository.DatastoreRepository
import ya.school.todoapp.domain.repository.NetworkRepository
import javax.inject.Inject

/**
 * Последовательность действий при попытке получить элемент по ID
 */
class GetItemUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val datastoreRepository: DatastoreRepository
) {
    suspend operator fun invoke(id: String): TodoResult<TodoItem> {
        when (val result = networkRepository.getItem(id)) {
            is TodoResult.Error -> return TodoResult.Error(result.message)
            is TodoResult.Success -> {
                datastoreRepository.setRevision(result.data.revision)
                return result
            }
        }
    }
}
package ya.school.todoapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.repository.DatastoreRepository
import ya.school.todoapp.domain.repository.NetworkRepository
import ya.school.todoapp.domain.repository.TodoItemsRepository
import javax.inject.Inject

class GetAllItemsUseCase @Inject constructor(
    val networkRepository: NetworkRepository,
    val datastoreRepository: DatastoreRepository,
    val todoItemsRepository: TodoItemsRepository
) {
    suspend operator fun invoke(): TodoResult<Flow<List<TodoItem>>> {
        when (val result = networkRepository.getAllItems()) {
            is TodoResult.Error -> return TodoResult.Error(result.message)
            is TodoResult.Success -> {
                datastoreRepository.setRevision(result.data.revision)
                todoItemsRepository.updateAllItems(result.data.list)
                return todoItemsRepository.getItems()
            }
        }
    }
}
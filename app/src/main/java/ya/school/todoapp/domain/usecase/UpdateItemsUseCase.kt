package ya.school.todoapp.domain.usecase

import kotlinx.coroutines.flow.first
import ya.school.todoapp.domain.entity.Revisioned
import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoResult
import ya.school.todoapp.domain.repository.DatastoreRepository
import ya.school.todoapp.domain.repository.NetworkRepository
import ya.school.todoapp.domain.repository.TodoItemsRepository
import java.util.Date
import javax.inject.Inject

class UpdateItemsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val datastoreRepository: DatastoreRepository,
    private val todoItemsRepository: TodoItemsRepository
) {
    suspend operator fun invoke() {
        val result = networkRepository.getAllItems()
        if (result is TodoResult.Success) {
            val lastOnline = datastoreRepository.getLastOnlineTimestamp()
            val localData = todoItemsRepository.getItems()
            val newData = mergeData(
                localData.first().toSet(),
                result.data.data.toSet(),
                lastOnline
            ).toList()
            datastoreRepository.setRevision(result.data.revision)
            todoItemsRepository.updateAllItems(newData)
            networkRepository.updateItems(Revisioned(newData, result.data.revision))
        }
    }

    private fun mergeData(
        localData: Set<TodoItem>,
        remoteData: Set<TodoItem>,
        lastOnline: Date
    ): Set<TodoItem> {
        val localAdded = localData.filter { it.createdAt >= lastOnline }.toSet()
        val localRemoved = remoteData.filter {
            it.createdAt < lastOnline && it.id !in localData.map { local -> local.id }
        }.toSet()
        val localModified = localData.filter {
            it.changedAt?.let { date -> date >= lastOnline } ?: false
        }.toSet()
        return remoteData.plus(localAdded).plus(localModified).minus(localRemoved)
    }
}
package ya.school.todoapp.data

import androidx.compose.runtime.toMutableStateList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.Continuation

@Singleton
class TodoItemsSource @Inject constructor() {
    private var nextId = 0
    private val items = (1..10).map {
        TodoItem(
            id = "${nextId++}",
            createdAt = Date(202020),
            importance =
            if (it % 5 == 0)
                TodoItem.Importance.Urgent
            else
                TodoItem.Importance.Low,
            isDone = false,
            text = "Какая-то задача"
        )
    }.toMutableStateList()

    private val _itemsFlow = MutableStateFlow<List<TodoItem>>(items)
    private val mutex = Mutex()

    val itemsFlow: Flow<List<TodoItem>>
        get() = _itemsFlow

    suspend fun addItem(
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ) = withLock {
        val newItem = TodoItem(
            id = "${nextId++}",
            text = text,
            importance = importance,
            deadline = deadline,
            isDone = false,
            createdAt = Date()
        )
        _itemsFlow.emit(
            items.apply {
                add(newItem)
            }
        )
    }

    suspend fun removeItem(itemId: String) = withLock {
        _itemsFlow.emit(
            items.apply {
                removeAt(
                    indexOfFirst { it.id == itemId }
                )
            }
        )
    }

    suspend fun changeItem(
        itemId: String,
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ) = withLock {
        _itemsFlow.emit(
            items.apply {
                val index = indexOfFirst { it.id == itemId }
                this[index] = this[index].copy(
                    text = text,
                    importance = importance,
                    deadline = deadline
                )
            }
        )
    }

    suspend fun changeCheckedStatus(
        itemId: String,
        isDone: Boolean
    ) = withLock {
        _itemsFlow.emit(
            items.apply {
                val index = indexOfFirst { it.id == itemId }
                this[index] = this[index].copy(
                    isDone = isDone
                )
            }
        )
    }

    suspend fun getItem(id: String): TodoItem = withLock {
        items.first { it.id == id }
    }

    private suspend fun<T> withLock(action: suspend () -> T): T {
        mutex.lock()
        val result = action()
        mutex.unlock()
        return result
    }
}
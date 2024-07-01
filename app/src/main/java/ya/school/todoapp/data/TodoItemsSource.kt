package ya.school.todoapp.data

import androidx.compose.runtime.toMutableStateList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import ya.school.todoapp.domain.entity.TodoResult
import java.util.Date
import java.util.Random
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoItemsSource @Inject constructor() {
    private var nextId = 0
    private val items = (1..30).map {
        TodoItem(
            id = "${nextId++}",
            createdAt = Date(202020),
            importance =
            when (it % 3) {
                0 -> TodoItem.Importance.Urgent
                1 -> TodoItem.Importance.Low
                else -> TodoItem.Importance.Regular
            },
            isDone = it % 4 == 0,
            text = (1..Random().nextInt() % 15).map { "Задача" }.joinToString()
        )
    }.toMutableStateList()

    private val _itemsFlow = MutableStateFlow<List<TodoItem>>(items)
    private val mutex = Mutex()

    val itemsFlow: TodoResult<Flow<List<TodoItem>>>
        get() = TodoResult.Success(_itemsFlow)

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
        try {
            _itemsFlow.emit(
                items.apply {
                    add(newItem)
                }
            )
            TodoResult.Success(Unit)
        } catch (e: RuntimeException) {
            TodoResult.Error("Не удалось добавить элемент")
        }
    }

    suspend fun removeItem(itemId: String) = withLock {
        try {
            _itemsFlow.emit(
                items.apply {
                    removeAt(
                        indexOfFirst { it.id == itemId }
                    )
                }
            )
            TodoResult.Success(Unit)
        } catch (e: RuntimeException) {
            TodoResult.Error("Не удалось удалить элемент")
        }
    }

    suspend fun changeItem(
        itemId: String,
        text: String,
        importance: TodoItem.Importance,
        deadline: Date?
    ) = withLock {
        try {
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
            TodoResult.Success(Unit)
        } catch (e: RuntimeException) {
            TodoResult.Error("Не удалось внести изменения")
        }
    }

    suspend fun changeCheckedStatus(
        itemId: String,
        isDone: Boolean
    ) = withLock {
        try {
            _itemsFlow.emit(
                items.apply {
                    val index = indexOfFirst { it.id == itemId }
                    this[index] = this[index].copy(
                        isDone = isDone
                    )
                }
            )
            TodoResult.Success(Unit)
        } catch (e: RuntimeException) {
            TodoResult.Error("Не удалось изменить статус задачи")
        }
    }

    suspend fun getItem(id: String): TodoResult<TodoItem> = withLock {
        try {
            TodoResult.Success(items.first { it.id == id })
        } catch (e: RuntimeException) {
            TodoResult.Error("Задача не найдена")
        }
    }

    private suspend fun<T> withLock(action: suspend () -> T): T {
        mutex.lock()
        val result = action()
        mutex.unlock()
        return result
    }
}
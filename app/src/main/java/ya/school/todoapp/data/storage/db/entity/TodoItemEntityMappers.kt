package ya.school.todoapp.data.storage.db.entity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ya.school.todoapp.domain.entity.TodoItem

fun TodoItemEntity.toTodoItem(): TodoItem {
    return TodoItem(
        id = id,
        text = text,
        importance = importance,
        deadline = deadline,
        isDone = done,
        createdAt = createdAt,
        changedAt = changedAt
    )
}

fun TodoItem.toTodoItemEntity(): TodoItemEntity {
    return TodoItemEntity(
        id = id,
        text = text,
        importance = importance,
        deadline = deadline,
        done = isDone,
        createdAt = createdAt,
        changedAt = changedAt ?: createdAt,
        lastUpdatedBy = "1"
    )
}

fun List<TodoItem>.toTodoItemEntities(): List<TodoItemEntity> {
    return map { it.toTodoItemEntity() }
}

fun List<TodoItemEntity>.toTodoItems(): List<TodoItem> {
    return map { it.toTodoItem() }
}

fun Flow<List<TodoItemEntity>>.toTodoItemsFlow(): Flow<List<TodoItem>> {
    return map { it.toTodoItems() }
}
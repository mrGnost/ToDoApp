package ya.school.todoapp.data.network.entity

import com.google.gson.annotations.SerializedName
import ya.school.todoapp.domain.entity.TodoItem
import java.util.Date

/**
 * Дата класс задачи - составного элемента реквестов и респонсов API бэкенда
 */
data class TodoItemDTO(
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long? = null,
    val done: Boolean,
    val color: String? = null,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("changed_at")
    val changedAt: Long,
    @SerializedName("last_updated_by")
    val lastUpdatedBy: Int
) {
    fun toTodoItem(revision: Int): TodoItem {
        return TodoItem(
            id = id,
            text = text,
            importance = when (importance) {
                "low" -> TodoItem.Importance.Low
                "important" -> TodoItem.Importance.Urgent
                else -> TodoItem.Importance.Regular
            },
            deadline = deadline?.let { Date(it) },
            isDone = done,
            createdAt = Date(createdAt),
            changedAt = Date(changedAt),
            revision = revision
        )
    }

    companion object {
        fun fromTodoItem(item: TodoItem): TodoItemDTO {
            return TodoItemDTO(
                id = item.id,
                text = item.text,
                importance = when (item.importance) {
                    TodoItem.Importance.Low -> "low"
                    TodoItem.Importance.Regular -> "basic"
                    TodoItem.Importance.Urgent -> "important"
                },
                deadline = item.deadline?.time,
                done = item.isDone,
                createdAt = item.createdAt.time,
                changedAt = item.changedAt?.time ?: item.createdAt.time,
                lastUpdatedBy = 0
            )
        }
    }
}

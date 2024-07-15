package ya.school.todoapp.domain.entity

import java.util.Date

/**
 * Дата класс, описывающий задачу
 */
data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val deadline: Date? = null,
    val isDone: Boolean,
    val files: List<String>? = null,
    val createdAt: Date,
    val changedAt: Date? = null
) {
    enum class Importance {
        Low,
        Regular,
        Urgent
    }
}

package ya.school.todoapp.data

import java.util.Date

data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val deadline: Date? = null,
    val isDone: Boolean,
    val createdAt: Date,
    val changedAt: Date? = null
) {
    enum class Importance {
        Low,
        Regular,
        Urgent
    }
}

package ya.school.todoapp.data.network.entity

import ya.school.todoapp.domain.entity.TodoItem

/**
 * Дата класс, описывающий респонс с одним элементом
 */
data class TodoItemResponseDTO(
    val status: String,
    val element: TodoItemDTO,
    val revision: Int
) {
    fun toTodoItem(): TodoItem {
        return element.toTodoItem(revision)
    }
}

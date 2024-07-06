package ya.school.todoapp.data.network.entity

import ya.school.todoapp.domain.entity.TodoItem

/**
 * Дата класс, описывающий респонс со списком элементов
 */
data class TodoListResponseDTO(
    val status: String,
    val list: List<TodoItemDTO>,
    val revision: Int
) {
    fun toTodoItems(): List<TodoItem> {
        return list.map { it.toTodoItem(revision) }
    }
}

package ya.school.todoapp.data.network.entity

import ya.school.todoapp.domain.entity.TodoItem

/**
 * Дата класс, описывающий реквест с одним элементом
 */
data class TodoItemRequestDTO(
    val status: String,
    val element: TodoItemDTO
) {
    companion object {
        fun fromTodoItem(item: TodoItem): TodoItemRequestDTO {
            return TodoItemRequestDTO(
                status = "200",
                element = TodoItemDTO.fromTodoItem(item)
            )
        }
    }
}

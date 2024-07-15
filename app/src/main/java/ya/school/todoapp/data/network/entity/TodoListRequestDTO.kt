package ya.school.todoapp.data.network.entity

import ya.school.todoapp.domain.entity.TodoItem

/**
 * Дата класс, описывающий реквест со списком элементов
 */
data class TodoListRequestDTO(
    val status: String,
    val list: List<TodoItemDTO>
) {
    companion object {
        fun fromTodoItems(items: List<TodoItem>): TodoListRequestDTO {
            return TodoListRequestDTO(
                status = "200",
                list = items.map { TodoItemDTO.fromTodoItem(it) }
            )
        }
    }
}

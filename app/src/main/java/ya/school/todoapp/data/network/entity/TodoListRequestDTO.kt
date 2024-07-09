package ya.school.todoapp.data.network.entity

import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoItemList

/**
 * Дата класс, описывающий реквест со списком элементов
 */
data class TodoListRequestDTO(
    val status: String,
    val list: List<TodoItemDTO>
) {
    companion object {
        fun fromTodoItems(items: TodoItemList): TodoListRequestDTO {
            return TodoListRequestDTO(
                status = "200",
                list = items.list.map { TodoItemDTO.fromTodoItem(it) }
            )
        }
    }
}

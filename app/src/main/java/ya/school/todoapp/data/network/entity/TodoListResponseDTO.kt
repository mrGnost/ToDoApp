package ya.school.todoapp.data.network.entity

import ya.school.todoapp.domain.entity.TodoItem
import ya.school.todoapp.domain.entity.TodoItemList

/**
 * Дата класс, описывающий респонс со списком элементов
 */
data class TodoListResponseDTO(
    val status: String,
    val list: List<TodoItemDTO>,
    val revision: Int
) {
    fun toTodoItems(): TodoItemList {
        return TodoItemList(
            list = list.map { it.toTodoItem(revision) },
            revision = revision
        )
    }
}

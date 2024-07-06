package ya.school.todoapp.domain.entity

data class TodoItemList(
    val list: List<TodoItem>,
    val revision: Int
)

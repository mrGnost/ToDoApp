package ya.school.todoapp.domain.entity

data class Revisioned<T>(
    val data: T,
    val revision: Int
)

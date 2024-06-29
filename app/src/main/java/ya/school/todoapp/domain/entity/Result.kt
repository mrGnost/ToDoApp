package ya.school.todoapp.domain.entity

sealed class TodoResult<in T> {
    data class Success<T>(val data: T) : TodoResult<T>()
    data class Error<T>(val message: String) : TodoResult<Unit>()
}
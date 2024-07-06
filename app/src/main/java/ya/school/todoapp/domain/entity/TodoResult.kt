package ya.school.todoapp.domain.entity

/**
 * Класс с информацией о состоянии результата, полученного из дата слоя
 */
sealed class TodoResult<in T> {
    data class Success<T>(val data: T) : TodoResult<T>()
    data class Error<T>(val message: String) : TodoResult<T>()
}
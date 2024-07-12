package ya.school.todoapp.domain.entity

/**
 * Класс с информацией о состоянии результата, полученного из дата слоя
 */
sealed class TodoResult<out T> {
    data class Success<T>(val data: T) : TodoResult<T>()
    data class Error(val message: String) : TodoResult<Nothing>()
}
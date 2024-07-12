package ya.school.todoapp.domain.entity

fun<T> TodoResult<Revisioned<T>>.toResult(): TodoResult<T> {
    return when (this) {
        is TodoResult.Success -> {
            TodoResult.Success(data.data)
        }
        is TodoResult.Error -> this
    }
}
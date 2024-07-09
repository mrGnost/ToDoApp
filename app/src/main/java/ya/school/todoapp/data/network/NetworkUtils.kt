package ya.school.todoapp.data.network

import retrofit2.Response
import ya.school.todoapp.domain.entity.TodoResult

/**
 * Объект со вспомогательными методами для работы с сетью
 */
object NetworkUtils {
    suspend fun<K, R, T : Response<K>> getResponse(
        mapper: (K) -> R,
        request: suspend () -> T
    ): TodoResult<R> {
        try {
            val response = request()
            return when (response.code()) {
                400 -> TodoResult.Error("Ошибка синхронизации данных")
                401 -> TodoResult.Error("Ошибка авторизации")
                404 -> TodoResult.Error("Ошибка: элемент не найден")
                500 -> TodoResult.Error("Ошибка на стороне сервера")
                else -> {
                    response.body()?.let {
                        TodoResult.Success(mapper(it))
                    } ?: TodoResult.Error("Ошибка: пустой ответ от сервера")
                }
            }
        } catch (e: Exception) {
            return TodoResult.Error("Не удалось связаться с сервером...")
        }
    }
}